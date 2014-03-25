package com.base.main.window;

import com.base.main.framework.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Levels {

    Color transparentGray;
    Color transparentLightGray;
    Texture tex;
    Font font1;
    Color color1;
    Color color2;
    Font font2;

    public Levels(Texture tex) {
        this.tex = tex;
        transparentGray = new Color(30, 30, 30, 160);
        transparentLightGray = new Color(200, 200, 200, 128);
        font1 = new Font("arial", Font.BOLD, 50);
        color1 = new Color(244, 67, 26);
        color2 = new Color(244, 67, 26, 64);
        font2 = new Font("arial", Font.BOLD, 28);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        // Antialiasing ON
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Background
        g2d.drawImage(tex.getMenuBackground(), 0, 0, 800 + 10, 600 + 10, null);

        // Dim screen
        g2d.setColor(transparentGray);
        g2d.fillRect(0, 0, 800 + 10, 600 + 10);
        
        // Level - Beach
        
        
        // Level - Forest
        
        
        // Level - Mountain
        

        // Select a level
        g2d.setColor(Color.white);
        g2d.setFont(font1);
        g2d.drawString("Select a level", 245, 100);
        
        // Coming soon text
        g2d.setFont(font2);
        g2d.drawString("Coming soon!", 310, 300);

        // Back Button
        g2d.setColor(transparentLightGray);
        g2d.fillRect(330, 430, 150, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(330, 430, 150, 50);
        g2d.setFont(font2);
        g2d.drawString("Back", 370, 465);

    }

}
