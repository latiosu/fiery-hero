package com.base.main.window;

import com.base.main.framework.GameObject;
import com.base.main.framework.ObjectId;
import com.base.main.objects.Block;
import java.util.LinkedList;

public class Camera {

    private float x, y;
    private float mx, my; // max x and max y
    private boolean inPlace = false;
    Handler handler;

    public Camera(float x, float y, Handler handler) {
        this.x = x;
        this.y = y;
        this.handler = handler;
        calculateBounds();
    }

    public void tick(LinkedList<GameObject> object) {

        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            if (object.get(i).getId() == ObjectId.Player && inPlace == true) {
                x += (-tempObject.getX() - x + (Game.WIDTH / 2)) * 0.08;
                y += (-tempObject.getY() - y + (Game.HEIGHT / 2)) * 0.16;
            }
            if (handler.object.get(i).getId() == ObjectId.Frame) {
                if (x - Game.WIDTH < -mx) {
                    x = -mx + Game.WIDTH;
                    inPlace = false;
                } else if (x > -32) {
                    x = -32;
                    inPlace = false;
                } else if (y > 0) {
                    y = 0;
                    inPlace = false;
                } else if (y - Game.HEIGHT < -my) {
                    y = -my + Game.HEIGHT;
                    inPlace = false;
                } else {
                    inPlace = true;
                }
            }
        }
    }

    private void calculateBounds() {
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ObjectId.Frame) {
                Block tempBlock = (Block) handler.object.get(i);
                if (mx < tempBlock.getX()) {
                    mx = tempBlock.getX();
                }
                if (my < tempBlock.getY()) {
                    my = tempBlock.getY();
                }
            }
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
