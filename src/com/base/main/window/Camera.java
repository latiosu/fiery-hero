package com.base.main.window;

import com.base.main.framework.GameObject;
import com.base.main.objects.Player;
import java.util.LinkedList;

public class Camera {

    private float x, y;
    private static boolean followOn = true;
    private static boolean zoomOn = false;

    Handler handler;

    float yMax;
    float xMax;
    float yZoomMax;
    float xZoomMax;

    public Camera(float x, float y, Handler handler) {
        this.x = x;
        this.y = y;
        this.handler = handler;
        handler.calculateBounds();
        yMax = Game.HEIGHT - handler.yBound;
        xMax = Game.WIDTH - handler.xBound;
        yZoomMax = Game.HEIGHT / 2 - handler.yBound;
        xZoomMax = Game.WIDTH / 2 - handler.xBound;
    }

    public void tick(LinkedList<GameObject> object) {
        Player playerObject = handler.playerObject;
        if (followOn) {
            if (zoomOn) {
                x += (-playerObject.getX() - x + (Game.WIDTH / 4)) * 0.08;
                y += (-playerObject.getY() - y + (Game.HEIGHT / 4)) * 0.16;
            } else {
                x += (-playerObject.getX() - x + (Game.WIDTH / 2)) * 0.08;
                y += (-playerObject.getY() - y + (Game.HEIGHT / 2)) * 0.16;
            }
        }
        checkBounds();
    }

    private void checkBounds() {
        if (x < xMax && !zoomOn) {
            x = xMax;
        }
        if (y < yMax && !zoomOn) {
            y = yMax;
        }
        if (x < xZoomMax && zoomOn) {
            x = xZoomMax;
        }
        if (y < yZoomMax && zoomOn) {
            y = yZoomMax;
        }
        if (x > -32) {
            x = -32;
        }
        if (y > 0) {
            y = 0;
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

    public static boolean getFollowOn() {
        return followOn;
    }

    public static boolean getZoomOn() {
        return zoomOn;
    }

    public static void setFollowOn(boolean value) {
        followOn = value;
    }

    public static void setZoomOn(boolean value) {
        zoomOn = value;
    }
}
