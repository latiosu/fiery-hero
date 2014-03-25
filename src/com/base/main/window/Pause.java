package com.base.main.window;

import com.base.main.framework.MouseInput;
import com.base.main.framework.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Pause {

    Handler handler;
    UI ui;
    Font font;
    Color transparentGray;
    Color transparentLightGray;
    Font font1;
    Color color1;
    Color color2;
    Font font2;

    public Pause(Handler handler, UI ui) {
        this.handler = handler;
        this.ui = ui;
        font = new Font("arial", Font.BOLD, 20);
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
        // Dim screen
        g.setColor(transparentGray);
        g.fillRect(0, 0, 800 + 10, 600 + 10);
        g.setFont(font);

        if (!MouseInput.menuPressed) {
            // Resume button
            g.setColor(transparentLightGray);
            g.fillRect(330, 190, 150, 50);
            g.setColor(Color.white);
            g.drawRect(330, 190, 150, 50);
            g.drawString("Resume", 370, 222);

            // Back to Menu button
            g.setColor(transparentLightGray);
            g.fillRect(280, 310, 250, 50);
            g.setColor(Color.white);
            g.drawRect(280, 310, 250, 50);
            g.drawString("Back to Menu", 340, 342);
        } else {
            g.setColor(Color.white);
            g.drawString("Are you sure?", 330, 250);
            // Yea button
            g.setColor(transparentLightGray);
            g.fillRect(250, 290, 100, 50);
            g.setColor(Color.white);
            g.drawRect(250, 290, 100, 50);
            g.drawString("Yea", 280, 322);
            // Nah button
            g.setColor(transparentLightGray);
            g.fillRect(435, 290, 100, 50);
            g.setColor(Color.white);
            g.drawRect(435, 290, 100, 50);
            g.drawString("Nah", 465, 322);
        }

    }
}
