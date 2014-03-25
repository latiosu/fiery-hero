package com.base.main.window;

import com.base.main.framework.Event;
import com.base.main.framework.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI {

    Handler handler;
    Timer t = new Timer();
    Font font20;
    Color transparentGray;
    Color transparentLightGray;
    Font font1;
    Color color1;
    Color color2;
    Font font2;
    Color hpColor = new Color(222, 46, 46);
    double totalScore;

    public UI(Handler handler) {
        this.handler = handler;
        t.setStartTime();
        t.setDuration(10);
        font20 = new Font("arial", Font.BOLD, 20);
        transparentGray = new Color(30, 30, 30, 160);
        transparentLightGray = new Color(200, 200, 200, 128);
        font1 = new Font("arial", Font.BOLD, 50);
        color1 = new Color(244, 67, 26);
        color2 = new Color(244, 67, 26, 64);
        font2 = new Font("arial", Font.BOLD, 28);
        totalScore = 0;
    }

    public void tick() {
        totalScore = calculateScore();
    }

    public void render(Graphics g) {
        // HP bar
        g.setColor(Color.black);
        g.drawRect(10, 10, 100, 30);
        g.setColor(Color.gray);
        g.fillRect(10, 10, 100, 30);
        if (handler.playerObject.isInvulnerable) {
            g.setColor(Color.yellow);
            g.fillRect(10, 10, handler.playerObject.getHP(), 30);
        } else {
            g.setColor(hpColor);
            g.fillRect(10, 10, handler.playerObject.getHP(), 30);
        }

        // Game info
        if (!t.checkTime()) {
            g.setColor(Color.white);
            g.setFont(font20);
            g.drawString("Use arrows/WASD to move, Space to jump, J to shoot", 180, 40);
            g.drawString("ESC to pause", 350, 70);

            Color fillColour = new Color(128, 128, 128, 80);
            g.setColor(fillColour);
            g.fillRect(230, 110, 345, 45);
            g.setColor(Color.yellow);
            g.drawRect(230, 110, 345, 45);
            g.drawString("Fend off the dangerous animals!", 250, 140);
        }
        // Game data
        g.setColor(Color.black);
        g.setFont(font20);
        g.drawString("HP", 120, 34);
        g.setColor(Color.white);
        g.drawString("Score: " + (long) totalScore, 10, 600);
        g.drawString("Enemies: " + Event.getCurrentEnemyCount(), 11, 581);

        if (Game.State == Game.STATE.GAMEOVER) {
            // Dim screen
            g.setColor(transparentGray);
            g.fillRect(0, 0, 800 + 10, 600 + 10);

            // Game over text
            g.setColor(Color.white);
            g.setFont(font1);
            g.drawString("~Game Over~", 245, 200);

            // Score text
            g.drawString("Score: " + (long) totalScore, 265, 280);

            // Menu button
            g.setFont(font20);
            g.setColor(transparentLightGray);
            g.fillRect(280, 410, 250, 50);
            g.setColor(Color.white);
            g.drawRect(280, 410, 250, 50);
            g.drawString("Back to Menu", 340, 442);
        }

    }

    public long calculateScore() {
        long score = 0;
        score += Event.getEnemyCount(0) * 50;
        score += Event.getEnemyCount(1) * 100;
        score += Event.getEnemyCount(2) * 200;
        return score;
    }
}
