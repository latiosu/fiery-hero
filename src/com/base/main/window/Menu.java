package com.base.main.window;

import com.base.main.framework.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Menu {

    Texture tex;
    Font font1;
    Color color1;
    Font font2;
    Color color2;
    Font font3;

    public Menu(Texture tex) {
        this.tex = tex;
        font1 = new Font("arial", Font.BOLD, 50);
        color1 = new Color(244, 67, 26);
        font2 = new Font("arial", Font.BOLD, 28);
        color2 = new Color(244, 67, 26, 64);
        font3 = new Font("arial", Font.BOLD, 15);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        // Background
        g.drawImage(tex.getMenuBackground(), 0, 0, 800 + 10, 600 + 10, null);

        // Title
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font1);
        g.setColor(color1);
        g.drawString("Fiery Hero", 80, 310);

        // Start Button
        g2d.setColor(color2);
        g2d.fillRect(480, 100, 150, 60);
        g2d.setColor(Color.gray);
        g2d.drawRect(480, 100, 150, 60);
        g2d.setFont(font2);
        g2d.setColor(Color.darkGray);
        g2d.drawString("Start", 518, 140);

        // Levels Button
        g2d.setColor(color2);
        g2d.fillRect(480, 260, 150, 60);
        g2d.setColor(Color.gray);
        g2d.drawRect(480, 260, 150, 60);
        g2d.setFont(font2);
        g2d.setColor(Color.darkGray);
        g2d.drawString("Levels", 510, 300);

        // Exit Button
        g2d.setColor(color2);
        g2d.fillRect(480, 420, 150, 60);
        g2d.setColor(Color.gray);
        g2d.drawRect(480, 420, 150, 60);
        g2d.setFont(font2);
        g2d.setColor(Color.darkGray);
        g2d.drawString("Exit", 525, 460);

        // Version info 
        g2d.setFont(font3);
        g2d.setColor(Color.black);
        g2d.drawString("Version: " + Game.version, 15, 595);
    }

}
