package com.base.main.framework;

import com.base.main.objects.Enemy;
import com.base.main.window.Game;
import com.base.main.window.Handler;
import java.util.LinkedList;

public class Event {

    private static int enemyCount0;
    private static int enemyCount1;
    private static int enemyCount2;
    private static int currentEnemyCount;
    Handler handler;

    public Event(Handler handler) {
        this.handler = handler;
    }

    public void tick(LinkedList<GameObject> object) {

        countEnemy(object);
        if (currentEnemyCount == 0) {
            addRandomEnemy(4);
        }
    }

    private int randomInt() {
        float num = (float) Math.random() * 2; // Number of enemy types - 1
        return Math.round(num);
    }

    private void countEnemy(LinkedList<GameObject> object) {
        currentEnemyCount = 0;
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            if (tempObject.getId() == ObjectId.Enemy) {
                currentEnemyCount++;
            }
        }
    }

    public static int getCurrentEnemyCount() {
        return currentEnemyCount;
    }

    public static int getEnemyCount(int type) {
        if (type == 0) {
            return enemyCount0;
        }
        if (type == 1) {
            return enemyCount1;
        }
        if (type == 2) {
            return enemyCount2;
        } else {
            return 0;
        }
    }

    private void addEnemy(int type) {
        handler.addObject(new Enemy(generateX(), generateY(), handler, type, ObjectId.Enemy));
    }

    private void addEnemy(int number, int type) {
        for (int i = 0; i < number; i++) {
            addEnemy(type);
        }
    }

    private void addRandomEnemy(int number) {
        for (int i = 0; i < number; i++) {
            addEnemy(randomInt());
        }
    }

    public static void enemyKilled(int type) {
        if (type == 0) {
            enemyCount0++;
        }
        if (type == 1) {
            enemyCount1++;
        }
        if (type == 2) {
            enemyCount2++;
        }
    }

    private int generateX() {
        int x = (int) (Math.random() * Game.WIDTH);
        if (x >= 32 && x <= Game.WIDTH - 32
                && (x > handler.playerObject.getX() + 100 || x < handler.playerObject.getX() - 100)) {
            return x;
        } else {
            return generateX();
        }
    }

    private int generateY() {
        int y = (int) (Math.random() * Game.HEIGHT);
        return y;
    }
}
