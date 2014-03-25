package com.base.main.window;

import com.base.main.framework.GameObject;
import com.base.main.framework.ObjectId;
import com.base.main.objects.Block;
import com.base.main.objects.Player;
import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    public GameObject[][] blockArray = new GameObject[512][512];

    private GameObject tempObject;
    public Player playerObject;

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
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            
            // Check that object is close to camera view
            if (tempObject.getX() >= playerObject.getX() - 1000
                    && tempObject.getX() <= playerObject.getX() + 880
                    && tempObject.getY() >= playerObject.getY() - 800
                    && tempObject.getY() <= playerObject.getY() + 680) {
                tempObject.render(g);
            }
        }
    }
    
    public void addBlock(GameObject object,int x, int y) {
        this.blockArray[x][y] = object;
    }
    
    public void removeBlock(GameObject object,int x, int y) {
        this.blockArray[x][y] = object;
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
                playerObject = (Player)tempObject;
            }
        }
    }
}
