package com.base.main.framework;

import com.base.main.objects.Player;
import com.base.main.objects.Projectile;
import com.base.main.window.Camera;
import com.base.main.window.Game;
import com.base.main.window.Handler;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler handler;
    Texture tex;
    Camera cam;
    Timer t = new Timer(); // Add limit projectile function
    Game game;
    public Player player;
    private double fireRate;
    private boolean canShoot;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean isShooting;

    public KeyInput(Handler handler, Texture tex, Camera cam, Game game) {
        this.handler = handler;
        this.tex = tex;
        this.cam = cam;
        this.game = game;
        canShoot = true;
        rightPressed = false;
        leftPressed = false;
        isShooting = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (Game.getState() == Game.STATE.GAME) {
            if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                if (leftPressed) {
                    player.setVelX(0);
                } else {
                    player.setVelX(4);
                    player.setDirection(1);
                }
                rightPressed = true;
            }
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                if (rightPressed) {
                    player.setVelX(0);
                } else {
                    player.setVelX(-4);
                    player.setDirection(0);
                }
                leftPressed = true;
            }
            if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            }

            if ((key == KeyEvent.VK_W || key == KeyEvent.VK_UP) && !player.isDoubleJumping) {
                if (player.isJumping()) {
                    player.setDoubleJumping(true);
                } else {
                    player.setJumping(true);
                }
                player.setVelY(-9);
            }
            if (key == KeyEvent.VK_SPACE /*!isShooting*/) { // Fire projectile
                System.out.println(t.startTime + " | " + t.duration);
                if (checkFireRate()) {
                    if (player.getDirection() == 0) {
                        // Player projectile left (with offset)
                        handler.addObject(new Projectile(player.getX() - 34, player.getY() - 12, 0, 0, tex, handler, ObjectId.Projectile));
                        t.setStartTime();
                    }
                    if (player.getDirection() == 1) {
                        // Player projectile right (with offset)
                        handler.addObject(new Projectile(player.getX() + 34, player.getY() - 12, 1, 0, tex, handler, ObjectId.Projectile));
                        t.setStartTime();
                    }

                    //isShooting = true;
                }
            }
        }
        if (Game.getState() == Game.STATE.MENU) {
            if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER) {
                game.enterGame();
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            if (Game.getState() == Game.STATE.GAME) {
                Game.setState(Game.STATE.PAUSE);
            } else if (Game.getState() == Game.STATE.PAUSE) {
                Game.setState(Game.STATE.GAME);
            }
        }

        if (key == KeyEvent.VK_Z) {
            if (cam.getZoomOn() == true) {
                cam.setZoomOn(false);
            } else {
                cam.setZoomOn(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (Game.getState() == Game.STATE.GAME) {
            if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                if (leftPressed) {
                    player.setVelX(-4);
                    player.setDirection(0);
                } else {
                    player.setVelX(0);
                }
                rightPressed = false;
            }
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                if (rightPressed) {
                    player.setVelX(4);
                    player.setDirection(1);
                } else {
                    player.setVelX(0);
                }
                leftPressed = false;
            }
            if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {

            }
            if (key == KeyEvent.VK_SPACE) {
                //isShooting = false;
            }
        }
    }

    public void setFireRate(double rate) {
        this.fireRate = rate;
        t.setDuration(fireRate);
    }

    private boolean checkFireRate() {
        if (t.checkTime()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void initCam(Camera cam) {
        this.cam = cam;
    }

    public void initPlayer(Player player) {
        this.player = player;
    }
}
