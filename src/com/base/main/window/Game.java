package com.base.main.window;

import com.base.main.framework.Event;
import com.base.main.framework.KeyInput;
import com.base.main.framework.MouseInput;
import com.base.main.framework.ObjectId;
import com.base.main.framework.Texture;
import com.base.main.objects.Block;
import com.base.main.objects.Enemy;
import com.base.main.objects.Player;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static String version = "2.7 beta";

    // Debug values
    private boolean eventOn = true;
    private boolean enemyOn = true;

    // Global variables
    public static int WIDTH, HEIGHT;
    public static long runningTime;
    public static int fps;
    private boolean running = false;
    private Thread thread;
    private BufferedImage level = null;
    BufferedImageLoader loader;
    Handler handler;
    static Texture tex;
    Menu menu;
    Pause pause;
    Levels levels;
    Camera cam;
    UI ui;
    Event evt;
    KeyInput ki; // Class reference

    public static enum STATE {

        GAME,
        MENU,
        LEVELS,
        PAUSE,
        GAMEOVER
    }

    private static STATE state = STATE.MENU;

    private void init() {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        runningTime = 0;
        loader = new BufferedImageLoader();
        tex = new Texture(loader);
        handler = new Handler();
        menu = new Menu(tex);
        pause = new Pause(handler, ui);
        levels = new Levels(tex);
        ki = new KeyInput(handler, tex, cam, this);
        ki.setFireRate(0.1);

        this.addKeyListener(ki);
        this.addMouseListener(new MouseInput(handler, this));
    }

    public void loadLevel(String levelName) {
        level = loader.toCompatibleImage(loader.loadImage(levelName)); // loading the level
        loadImageLevel(level);
        cam = new Camera(0, 0, handler);
        ui = new UI(handler);
        evt = new Event(handler);
        ki.initCam(cam);
        ki.initPlayer(handler.playerObject);
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        init();
        this.requestFocus();

        long startTime = System.nanoTime();
        long previousTime = System.nanoTime();
        long tempTime = System.nanoTime();
        long currentTime;

        double updateRate = 60.0;
        long ns = 1000000000;
        double delta = 0;
        int updates = 0;
        int frames = 0;

        while (running) {
            runningTime = (System.nanoTime() - startTime) / ns;
            currentTime = System.nanoTime();
            delta += ((currentTime - previousTime) * updateRate) / ns;
            previousTime = currentTime;

            // Makes the game update 60 times per second
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }

            // Update as fast as possible
            render();
            frames++;

            // Info printed to console each second
            if ((currentTime - tempTime) >= ns) {
                fps = frames;
                System.out.println("FPS: " + fps + " TICKS: " + updates
                        + " TIME: " + runningTime + " STATE: " + Game.state);
                if (state == STATE.GAME) {
                    System.out.println(handler.playerObject.getX() + " | " + handler.playerObject.getY());
                }
                frames = 0;
                updates = 0;
                tempTime = currentTime;
            }
        }
    }

    private void tick() {
        if (state == STATE.MENU) {
            menu.tick();
        }

        if (state == STATE.GAME) {
            handler.tick();
            cam.tick(handler.object);
            if (eventOn) {
                evt.tick(handler.object);
            }
            ui.tick();
        }
        if (state == STATE.PAUSE) {
            pause.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        if (state == STATE.MENU) {
            menu.render(g);
        }

        if (state == STATE.LEVELS) {
            levels.render(g);
        }

        if (state == STATE.GAME || state == STATE.PAUSE || state == STATE.GAMEOVER) {
            g2d.drawImage(tex.getGameBackground(), 0, 0, 800 + 10, 600 + 10, null);

            if (cam.getZoomOn()) {
                g2d.scale(2.0, 2.0);
                g2d.translate(cam.getX(), cam.getY()); // Cam start

                handler.render(g);

                g2d.translate(-cam.getX(), -cam.getY()); // Cam end

                g2d.scale(0.5, 0.5);
            } else {
                g2d.translate(cam.getX(), cam.getY()); // Cam start

                handler.render(g);

                g2d.translate(-cam.getX(), -cam.getY()); // Cam end    
            }

            ui.render(g);

        }
        if (state == STATE.PAUSE) {
            pause.render(g);
        }
        g.dispose();
        bs.show();
    }

    public void loadImageLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                // Static objects
                // Add dirt block for white block
                if (red == 255 && green == 255 && blue == 255) {
                    handler.addBlock(new Block(xx * 32, yy * 32, 1, ObjectId.Block), xx, yy, 1);
                } // Add grass block for grey block
                else if (red == 128 && green == 128 && blue == 128) {
                    handler.addBlock(new Block(xx * 32, yy * 32, 2, ObjectId.Block), xx, yy, 1);
                } // Add invisible block 1 for green block
                else if (red == 0 && green == 255 && blue == 0) {
                    handler.addBlock(new Block(xx * 32, yy * 32, 3, ObjectId.Frame), xx, yy, 2);
                } // Dynamic objects
                // Add enemy for red block
                else if (red == 255 && green == 0 && blue == 0 && enemyOn) {
                    handler.addObject(new Enemy(xx * 32, yy * 32, handler, 0, ObjectId.Enemy));
                } // Add player for blue block
                else if (red == 0 && green == 0 && blue == 255) {
                    handler.addObject(new Player(xx * 32, yy * 32, handler, ObjectId.Player));
                    handler.setPlayer();
                } // Add empty block
                else {
//                    handler.addBlock(new Block(xx * 32, yy * 32, 0, ObjectId.Block), xx, yy, 0);
                }
            }
        }
    }

    public static STATE getState() {
        return state;
    }

    public static void setState(STATE s) {
        state = s;
    }

    public void enterGame() {
        handler.object.clear(); // Empty dynamic object list
        handler.initArrays(); // Prepares static object list
        loadLevel("/level.png");
        state = Game.STATE.GAME;
    }

    public static Texture getInstance() {
        return tex;
    }

    public static void main(String[] args) {
        new Window(800, 600, "Fiery Hero", new Game());
    }
}
