package com.base.main.objects;

import com.base.main.framework.GameObject;
import com.base.main.framework.ObjectId;
import com.base.main.framework.Texture;
import com.base.main.window.Game;
import com.base.main.window.Handler;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Projectile extends GameObject {

    Handler handler;
    Texture tex;
    private int width, height;
    private int direction;
    private int speed;
    private float px;
    private float py;

    public Projectile(float x, float y, int direction, int type, Texture tex, Handler handler, ObjectId id) {
        super(x, y + 12, id);
        this.handler = handler;
        this.tex = tex;
        this.direction = direction;
        if (type == 0) {
            speed = 10;
        }
        setPlayerPoint(x, y);
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        if (direction == 0) {
            x -= speed;
        }
        if (direction == 1) {
            x += speed;
        }
        if (x > px + Game.WIDTH / 2 || x < px - Game.WIDTH / 2) {
            handler.object.remove(this);
        }
        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object) {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.Frame) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(this);
                }
            }
        }
    }

    private void setPlayerPoint(float px, float py) {
        this.px = px;
        this.py = py;
    }

    @Override
    public void render(Graphics g) {
        if (direction == 0) { // left
            g.drawImage(tex.projectile[0], (int) x, (int) y, null);
        }
        if (direction == 1) { // right
            g.drawImage(tex.projectile[1], (int) x, (int) y, null);
        }
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }
}
