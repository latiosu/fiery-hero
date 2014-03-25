package com.base.main.objects;

import com.base.main.framework.Animation;
import com.base.main.framework.GameObject;
import com.base.main.framework.ObjectId;
import com.base.main.framework.Texture;
import com.base.main.framework.Timer;
import com.base.main.window.Game;
import com.base.main.window.Handler;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Player extends GameObject {

    final private float width, height;
    final private float MAX_SPEED;
    private float gravity;
    private int direction;
    private int HP;
    private Handler handler;
    Texture tex;
    Animation walkLeftAnim;
    Animation walkRightAnim;
    Animation invulnWalkLeftAnim;
    Animation invulnWalkRightAnim;
    Timer t;

    public boolean isClimbing = false;
    public boolean isWalking = false;
    public boolean isHit = false;
    public boolean isInvulnerable = false;
    public boolean isDoubleJumping = false;
    public boolean isAlive = true;

    public Player(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
        tex = Game.getInstance();
        t = new Timer();
        t.setDuration(2);
        walkLeftAnim = new Animation(8, tex.player[0], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5]);
        walkRightAnim = new Animation(8, tex.player[6], tex.player[7], tex.player[8], tex.player[9], tex.player[10], tex.player[11]);
        invulnWalkLeftAnim = new Animation(8, tex.player[12], tex.player[13], tex.player[14], tex.player[15], tex.player[16], tex.player[17]);
        invulnWalkRightAnim = new Animation(8, tex.player[18], tex.player[19], tex.player[20], tex.player[21], tex.player[22], tex.player[23]);
        width = 32;
        height = 48;
        MAX_SPEED = 10;
        gravity = 0.4f;
        direction = 0;
        HP = 100;
        isAlive = true;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        if (HP <= 0) {
            HP = 0;
            isInvulnerable = true;
            isAlive = false;
            Game.State = Game.STATE.GAMEOVER;
        }

        x += velX;
        y += velY;

        if (falling || jumping) {
            velY += gravity;

            if (velY > MAX_SPEED) {
                velY = MAX_SPEED;
            }
        }
        Collision(object);
        removeInvuln();
        walkLeftAnim.runAnimation();
        walkRightAnim.runAnimation();
        invulnWalkLeftAnim.runAnimation();
        invulnWalkRightAnim.runAnimation();
    }

    private void Collision(LinkedList<GameObject> object) {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            // Player physics
            if ((tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.Frame)) {
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + 32;
                    velY = 0;
                    falling = false;
                    jumping = true;
                }

                if (getBoundsBottom().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                    isDoubleJumping = false;
                } else {
                    falling = true;
                }

                // Right
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - width;

                }

                // Left
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + 32;

                }
            }

            if (tempObject.getId() == ObjectId.Enemy) {
                if (getBounds().intersects(tempObject.getBounds()) && !isInvulnerable) {
                    Enemy e = (Enemy) handler.object.get(i);
//                    if (e.getDirection() == 0) {
//                        velX = -6;
//                        velY = -2;
//                        setJumping(true);
//                    }
//                    if (e.getDirection() == 1) {
//                        velX = 6;
//                        velY = -2;
//                        setJumping(true);
//                    }
                    if (HP > 0 && isHit == false) {
                        HP -= 15; // Basic damage value
                        isHit = true;
                        isInvulnerable = true;
                    }
                    t.setStartTime();
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {
        if (isInvulnerable) {
            if (velX != 0) {
                if (direction == 0) {
                    invulnWalkLeftAnim.drawAnimation(g, (int) x, (int) y - 5, (int) width, (int) height + 12);
                }
                if (direction == 1) {
                    invulnWalkRightAnim.drawAnimation(g, (int) x, (int) y - 5, (int) width, (int) height + 12);
                }
            } else {
                if (direction == 0) {
                    g.drawImage(tex.player[12], (int) x, (int) y - 5, (int) width, (int) height + 12, null);
                }
                if (direction == 1) {
                    g.drawImage(tex.player[18], (int) x, (int) y - 5, (int) width, (int) height + 12, null);
                }
            }
        } else {
            if (velX != 0) {
                if (direction == 0) {
                    walkLeftAnim.drawAnimation(g, (int) x, (int) y - 5, (int) width, (int) height + 12);
                }
                if (direction == 1) {
                    walkRightAnim.drawAnimation(g, (int) x, (int) y - 5, (int) width, (int) height + 12);
                }
            } else {
                if (direction == 0) {
                    g.drawImage(tex.player[0], (int) x, (int) y - 5, (int) width, (int) height + 12, null);
                }
                if (direction == 1) {
                    g.drawImage(tex.player[6], (int) x, (int) y - 5, (int) width, (int) height + 12, null);
                }
            }
        }

    }

    public void removeInvuln() {
        if (t.checkTime()) {
            isInvulnerable = false;
            isHit = false;
            t.setStartTime();
        }
    }

    public void setDoubleJumping(boolean b) {
        isDoubleJumping = b;
    }

    public void setWalking(boolean b) {
        isWalking = b;
    }

    public boolean getWalking() {
        return isWalking;
    }

    public void setDirection(int i) {
        direction = i; // Left is 0, Right is 1;
    }

    public int getDirection() {
        return direction;
    }

    public int getHP() {
        return HP;
    }

    public boolean isInvulnerable() {
        return isInvulnerable;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle((int) ((int) x + (width / 2) - ((width / 2) / 2)), (int) ((int) y + (height / 2)), (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) ((int) x + (width / 2) - ((width / 2) / 2)), (int) y, (int) width / 2 - 4, (int) height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) ((int) x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 5, (int) 5, (int) height - 10);
    }

}
