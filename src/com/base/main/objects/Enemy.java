package com.base.main.objects;

import com.base.main.framework.Event;
import com.base.main.framework.GameObject;
import com.base.main.framework.ObjectId;
import com.base.main.framework.Texture;
import com.base.main.framework.Timer;
import com.base.main.window.Game;
import com.base.main.window.Handler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Enemy extends GameObject {

    private int width, height;
    private int type;
    private float gravity;
    private float MAX_SPEED;
    private int direction = 0;
    private float lastX;
    private float speed;
    private int maxHP;
    private int currentHP;
    private int healthSize = 50; // for health bar in px
    private int jumpRadius; // Radius of block to jump in px
    private double jumpThreshold; // Only used for rabbit enemy

    public int bitX = 0;
    public int bitBotY = 0;
    public int bitTopY = 0;

    private boolean damaged = false;
    private Handler handler;
    Texture tex;
    Timer t = new Timer();

    public Enemy(float x, float y, Handler handler, int type, ObjectId id) {
        super(x, y, id);
        this.type = type;
        defineEnemy(type);
        this.handler = handler;
        tex = Game.getInstance();
        lastX = x;
        updateBitPosition();
    }

    private void defineEnemy(int type) {
        if (type == 0) { // Crocodile
            width = 32;
            height = 64;
            gravity = 0.4f;
            MAX_SPEED = 10;
            speed = 1.5f;
            maxHP = 15;
            currentHP = maxHP;
            jumpRadius = 40;
        }
        if (type == 1) { // Rabbit
            width = 32;
            height = 32;
            gravity = 0.4f;
            MAX_SPEED = 10;
            speed = 1f;
            maxHP = 20;
            currentHP = maxHP;
            jumpRadius = 20;
            jumpThreshold = 0.9;
        }
        if (type == 2) { // Bird
            width = 32;
            height = 32;
            gravity = 0.2f;
            MAX_SPEED = 10;
            speed = 2f;
            maxHP = 10;
            currentHP = maxHP;
            jumpRadius = 0;
        }
        // Initializes timed event
        t.setStartTime();
        t.setDuration(1);
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        if (currentHP <= 0) {
            t.setDuration(0); // Stops timed event
            Event.enemyKilled(type);
            handler.removeObject(this);
        }

        x += velX;
        y += velY;
        if (falling || jumping) {
            velY += gravity;

            if (velY > MAX_SPEED) {
                velY = MAX_SPEED;
            }
        }
        updateRate();
        updateBitPosition();
        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object) {
        

        // Block collisions
        if (handler.blockCollision(bitX, bitBotY) || handler.blockCollision(bitX + 1, bitBotY)) {
            if (falling) {
                y = bitBotY * 32 - height;
                velY = 0;
            }
            falling = false;
            jumping = false;
        } else {
            falling = true;
        }
        if (handler.blockCollision(bitX, bitTopY) || handler.blockCollision(bitX + 1, bitTopY)) {
            y = bitTopY * 32 + 32;
            velY = 0;
            falling = true;
            jumping = false;

        }
        // Right
        if (handler.blockCollision(bitX + 1, bitBotY - 1)) {
            x = bitX * 32;
        }

        // Left
        if (handler.blockCollision(bitX, bitBotY - 1)) {
            x = (bitX + 1) * 32;
        }

        // Jump when 64px away from block
//        if (getJumpBounds().intersects(tempBlock.getBounds()) && !isJumping()
//                && tempBlock.getId() != ObjectId.Frame) {
        if (handler.blockCollision(bitX-1, bitBotY-1) || handler.blockCollision(bitX+1, bitBotY-1)) {
            setJumping(true);
            jump(type);
        }

        // Check with dynamic objects
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ObjectId.Projectile) { // Projectile
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    updateHealth(4); // value is basic projectile damage
                    damaged = true;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (type == 0) {
            if (direction == 0) {
                g.drawImage(tex.enemy[0], (int) x, (int) y, (int) width, (int) height + 12, null);
            }
            if (direction == 1) {
                g.drawImage(tex.enemy[1], (int) x, (int) y, (int) width, (int) height + 12, null);
            }
        }
        if (type == 1) {
            if (direction == 0) {
                g.drawImage(tex.enemy[4], (int) x, (int) y, (int) width, (int) height, null);
            }
            if (direction == 1) {
                g.drawImage(tex.enemy[5], (int) x, (int) y, (int) width, (int) height, null);
            }
        }
        if (type == 2) {
            if (direction == 0) {
                g.drawImage(tex.enemy[2], (int) x, (int) y, (int) width, (int) height, null);
            }
            if (direction == 1) {
                g.drawImage(tex.enemy[3], (int) x, (int) y, (int) width, (int) height, null);
            }
        }
        showHealthBar(g);
    }

    private void updateBitPosition() {
//        float floatX = this.x / 32;
//        float floatY = (this.y+height) / 32;
        this.bitX = (int) Math.floor(this.x / 32);
        this.bitBotY = (int) Math.floor((this.y + height) / 32);
        this.bitTopY = (int) Math.floor((this.y) / 32);
//        System.out.println("floats: " + floatX + " " + floatY + " | " + bitX + " " + bitY);
    }

    private void updateHealth(int damage) {
        int divisor = maxHP / damage;
        int value = 50 / divisor;
        healthSize -= value;
        currentHP -= damage;
    }

    private void showHealthBar(Graphics g) {
        if (damaged) {
            g.setColor(Color.black);
            g.drawRect((int) x - 8, (int) (y - 20), 50, 3);
            g.setColor(Color.gray);
            g.fillRect((int) x - 8, (int) (y - 20), 50, 3);
            g.setColor(Color.red);
            g.fillRect((int) x - 8, (int) (y - 20), healthSize, 3);
        }
    }

    private void followPlayer() {
        if (type != 2) {
            if (x == handler.playerObject.getX() && !isJumping()) {
                velX = 0;
            }
            if (x < handler.playerObject.getX() && !isJumping()) {
                direction = 1;
                velX = speed;
            }
            if (x > handler.playerObject.getX() && !isJumping()) {
                direction = 0;
                velX = -speed;
            }
            if (type == 1) {
                if (Math.random() <= jumpThreshold) {
                    jump(type);
                }
            }
        } else {
            if (x < handler.playerObject.getX()) {
                direction = 1;
                velX = speed;
            }
            if (x > handler.playerObject.getX()) {
                direction = 0;
                velX = -speed;
            }
            if (y > handler.playerObject.getY()) {
                velY = -7;
            }
            if (y < handler.playerObject.getY()) {
                velY = -4;
            }
        }
    }

    public void jump(int type) {
        setJumping(true);
        if (type == 0) {
            velY = -8;
        }
        if (type == 1) {
            velY = -7;
        }
        if (type == 2) {

        }
    }

    public void updateRate() {
        if (t.checkTime()) {
            followPlayer();
            t.setStartTime();
        }
    }

    public void setDirection(int i) {
        direction = i; // Left is 0, Right is 1;
    }

    public int getDirection() {
        return direction;
    }

    public int getType() {
        return type;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle((int) x + 6, (int) (y + (height / 2)), (int) width / 2 + 4, (int) height / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) x + 6, (int) y, (int) width / 2 + 4, (int) height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) (x + width - 5), (int) y + 5, 5, (int) height - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 5, 5, (int) height - 10);
    }
}
