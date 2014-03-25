package com.base.main.framework;

import com.base.main.objects.Player;
import com.base.main.objects.Projectile;
import com.base.main.window.Game;
import com.base.main.window.Handler;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler handler;
    Texture tex;
    Timer t = new Timer(); // Add limit projectile function
    private boolean canShoot;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean isShooting;

    public KeyInput(Handler handler, Texture tex) {
        this.handler = handler;
        this.tex = tex;
        canShoot = true;
        rightPressed = false;
        leftPressed = false;
        isShooting = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (Game.State == Game.STATE.GAME) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);

                if (tempObject.getId() == ObjectId.Player) {
                    if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                        if (leftPressed) {
                            tempObject.setVelX(0);
                        } else {
                            tempObject.setVelX(4);
                            handler.playerObject.setDirection(1);
                        }
                        rightPressed = true;
                    }
                    if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                        if (rightPressed) {
                            tempObject.setVelX(0);
                        } else {
                            tempObject.setVelX(-4);
                            handler.playerObject.setDirection(0);
                        }
                        leftPressed = true;
                    }
                    if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                    }

                    if ((key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W || key == KeyEvent.VK_UP) && !handler.playerObject.isDoubleJumping /*&& !Player.isProne*/) {
                        if (tempObject.isJumping()) {
                            handler.playerObject.setDoubleJumping(true);
                        } else {
                            tempObject.setJumping(true);
                        }
                        tempObject.setVelY(-9);
                    }
                }
                if (key == KeyEvent.VK_J && !isShooting) { // Fire projectile
                    if (handler.playerObject.getDirection() == 0) {
                        handler.addObject(new Projectile(handler.playerObject.getX() - 34, handler.playerObject.getY() - 12, 0, 0, tex, handler, ObjectId.Projectile)); // Player projectile left (with offset)
                    }
                    if (handler.playerObject.getDirection() == 1) {
                        handler.addObject(new Projectile(handler.playerObject.getX() + 34, handler.playerObject.getY() - 12, 1, 0, tex, handler, ObjectId.Projectile)); // Player projectile right (with offset)
                    }

                    isShooting = true;
                }
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            if (Game.State == Game.STATE.GAME) {
                Game.State = Game.STATE.PAUSE;
            } else if (Game.State == Game.STATE.PAUSE) {
                Game.State = Game.STATE.GAME;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (Game.State == Game.STATE.GAME) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);

                if (tempObject.getId() == ObjectId.Player) {
                    if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                        if (leftPressed) {
                            tempObject.setVelX(-4);
                            handler.playerObject.setDirection(0);
                        } else {
                            tempObject.setVelX(0);
                        }
                        rightPressed = false;
                    }
                    if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                        if (rightPressed) {
                            tempObject.setVelX(4);
                            handler.playerObject.setDirection(1);
                        } else {
                            tempObject.setVelX(0);
                        }
                        leftPressed = false;
                    }
                    if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                        
                    }
                    if (key == KeyEvent.VK_J) {
                        isShooting = false;
                    }
                }
            }
        }
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
}
