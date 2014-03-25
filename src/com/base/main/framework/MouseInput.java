package com.base.main.framework;

import com.base.main.window.Game;
import com.base.main.window.Handler;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    public static boolean menuPressed = false;
    public static boolean menuConfirmed = false;
    Handler handler;
    Game game;

    public MouseInput(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {
        int key = e.getButton();
        int mx = e.getX();
        int my = e.getY();
        if (Game.State == Game.STATE.MENU) {
            if (mx >= 480 && mx <= 630) {
                if (my >= 100 && my <= 160) { // start button
                    handler.object.clear(); // Empty objects list
                    game.loadLevel("/level.png");
                    Game.State = Game.STATE.GAME;
                }
                if (my >= 260 && my <= 320) { // levels button
                    Game.State = Game.STATE.LEVELS;
                }
                if (my >= 420 && my <= 480) { // exit button
                    System.exit(0);
                }
            }
        }
        if (Game.State == Game.STATE.PAUSE && !menuPressed) {
            if (mx >= 340 && mx <= 480 && my >= 190 && my <= 240) { // Resume
                Game.State = Game.STATE.GAME;
            }
            if (mx >= 280 && mx <= 530 && my >= 310 && my <= 360) { // Menu
                menuPressed = true;
            }
        }
        if (Game.State == Game.STATE.PAUSE && menuPressed) {
            if (mx >= 250 && mx <= 350 && my >= 290 && my <= 340) { // Yea
                Game.State = Game.STATE.MENU;
                menuPressed = false;
            }
            if (mx >= 435 && mx <= 535 && my >= 290 && my <= 340) { // Nah
                menuPressed = false;
            }
        }

        if (Game.State == Game.STATE.LEVELS) {
            // Add level selections here

            if (mx >= 330 && mx <= 480 && my >= 430 && my <= 480) { // Back
                Game.State = Game.STATE.MENU;
                menuPressed = false;
            }
        }

        if (Game.State == Game.STATE.GAMEOVER) {
            if (mx >= 280 && mx <= 530 && my >= 410 && my <= 460) { // Menu
                Game.State = Game.STATE.MENU;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        int key = e.getButton();
        int mx = e.getX();
        int my = e.getY();
    }
}
