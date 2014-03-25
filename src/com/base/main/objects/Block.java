package com.base.main.objects;

import com.base.main.framework.GameObject;
import com.base.main.framework.ObjectId;
import com.base.main.framework.Texture;
import com.base.main.window.Game;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Block extends GameObject {
    
    Texture tex = Game.getInstance();
    private int type;
    private float width = 32;
    private float height = 32;

    public Block(float x, float y, int type, ObjectId id) {
        super(x, y, id);
        this.type = type;
    }
    
    @Override
    public void tick(LinkedList<GameObject> object) {
    }

    @Override
    public void render(Graphics g) {
        if(type == 0) { // dirt block
            g.drawImage(tex.block[0],(int)x, (int)y, null);
        }
        if(type == 1) { // grass block
            g.drawImage(tex.block[1],(int)x, (int)y, null);
        }
        if(type == 2) { // invis block
            g.drawImage(tex.block[2],(int)x, (int)y, null);
        }
    }
    
    public int getType() {
        return type;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int) width, (int) height);
    }
}
