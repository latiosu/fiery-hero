package com.base.main.window;

import com.base.main.framework.GameObject;
import com.base.main.framework.ObjectId;
import com.base.main.objects.Block;
import com.base.main.objects.Player;
import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

    private final int MAP_SIZE = 512; // pixels
    public float xBound, yBound; // max x and max y

    public LinkedList<GameObject> object = new LinkedList<>();
    public Block[][] blockArray = new Block[MAP_SIZE][MAP_SIZE];
    public int[][] bitArray = new int[MAP_SIZE][MAP_SIZE];

    private GameObject tempObject;
    public Player playerObject; // global player object reference (one player only)

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            // Update player reference
            if (tempObject.getId() == ObjectId.Player) {
                playerObject = (Player) tempObject;
            }
            tempObject.tick(object);
        }
    }

    public void render(Graphics g) {
        // Render Blocks
        for (int x = 0; x < blockArray.length; x++) {
            for (int y = 0; y < blockArray.length; y++) {
                if (blockArray[x][y] != null) {
                    Block tempBlock = blockArray[x][y];
                    if (Camera.getZoomOn()) {
                        // Limit render distance - Zoomed
                        if (tempBlock.getX() >= playerObject.getX() - 500
                                && tempBlock.getX() <= playerObject.getX() + 440
                                && tempBlock.getY() >= playerObject.getY() - 400
                                && tempBlock.getY() <= playerObject.getY() + 340) {
                            tempBlock.render(g);
                        }
                    } else {
                        // Limit render distance - Unzoomed
                        if (tempBlock.getX() >= playerObject.getX() - 1000
                                && tempBlock.getX() <= playerObject.getX() + 880
                                && tempBlock.getY() >= playerObject.getY() - 800
                                && tempBlock.getY() <= playerObject.getY() + 680) {
                            tempBlock.render(g);
                        }
                    }
                }
            }
        }
        // Render Objects
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);

            if (Camera.getZoomOn()) {
            // Limit render distance - Zoomed
                if (tempObject.getX() >= playerObject.getX() - 500
                        && tempObject.getX() <= playerObject.getX() + 440
                        && tempObject.getY() >= playerObject.getY() - 400
                        && tempObject.getY() <= playerObject.getY() + 340) {
                    tempObject.render(g);
                }
            } else {
                // Limit render distance - Unzoomed
                if (tempObject.getX() >= playerObject.getX() - 1000
                        && tempObject.getX() <= playerObject.getX() + 880
                        && tempObject.getY() >= playerObject.getY() - 800
                        && tempObject.getY() <= playerObject.getY() + 680) {
                    tempObject.render(g);
                }
            }
        }
    }

    public void initArrays() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                bitArray[i][j] = 0;
                blockArray[i][j] = null;
            }
        }
    }

    public boolean blockCollision(int x, int y) {
        if (x <= 0 || y <= 0 || x >= MAP_SIZE || y >= MAP_SIZE) {
            return true;
        } else if (bitArray[x][y] == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean blockCollision(float x, float y) {
        int newX = (int) Math.floor(x / 32);
        int newY = (int) Math.floor(y / 32);
        if (x <= 0 || y <= 0 || x >= xBound || y >= yBound) {
            return true;
        }
        if (bitArray[newX][newY] == 1) {
            return true;
        } else {
            return false;
        }
    }

    // Determines the maximum distance of blocks
    public void calculateBounds() {
        for (int i = 0; i < blockArray.length; i++) {
            for (int j = 0; j < blockArray.length; j++) {
                if (bitArray[i][j] == 2) {
                    Block tempBlock = blockArray[i][j];
                    if (xBound < tempBlock.getX()) {
                        xBound = tempBlock.getX();
                    }
                    if (yBound < tempBlock.getY()) {
                        yBound = tempBlock.getY();
                    }
                }
            }
        }
    }

    // This method also sets the corresponding boolean array position to true
    public void addBlock(Block block, int x, int y, int type) {
        this.blockArray[x][y] = block;
        this.bitArray[x][y] = type;
    }

    public void removeBlock(int x, int y) {
        this.blockArray[x][y] = null;
        this.bitArray[x][y] = 0;
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

//    public Player getPlayer() {
//        for (int i = 0; i < object.size(); i++) {
//            tempObject = object.get(i);
//
//            if (tempObject.getId() == ObjectId.Player) {
//                return (Player) tempObject;
//            }
//        }
//        return null;
//    }
    // Initialize the playerObject
    public void setPlayer() {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);

            if (tempObject.getId() == ObjectId.Player) {
                playerObject = (Player) tempObject;
            }
        }
    }
}
