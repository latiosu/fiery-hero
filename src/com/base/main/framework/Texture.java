package com.base.main.framework;

import com.base.main.window.BufferedImageLoader;
import java.awt.image.BufferedImage;

public class Texture {

    BufferedImageLoader loader;
    
    SpriteSheet bs, ps, es, js;
    private BufferedImage block_sheet = null;
    private BufferedImage player_sheet = null;
    private BufferedImage enemy_sheet = null;
    private BufferedImage projectile_sheet = null;
    private BufferedImage gameBackground = null;
    private BufferedImage menuBackground = null;
    
    public BufferedImage[] block = new BufferedImage[3];
    public BufferedImage[] player = new BufferedImage[28];
    public BufferedImage[] enemy = new BufferedImage[6];
    public BufferedImage[] projectile = new BufferedImage[2];

    public Texture(BufferedImageLoader loader) {

        this.loader = loader;

        block_sheet = loader.toCompatibleImage(loader.loadImage("/block_sheet.png"));
        player_sheet = loader.toCompatibleImage(loader.loadImage("/player_sheet.png"));
        enemy_sheet = loader.toCompatibleImage(loader.loadImage("/enemy_sheet.png"));
        projectile_sheet = loader.toCompatibleImage(loader.loadImage("/projectile_sheet.png"));
        gameBackground = loader.toCompatibleImage(loader.loadImage("/background.png"));
        menuBackground = loader.toCompatibleImage(loader.loadImage("/menu_background.png"));

        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);
        es = new SpriteSheet(enemy_sheet);
        js = new SpriteSheet(projectile_sheet);
        
        getTextures();
    }
    
    private void getTextures() {
        block[0] = bs.grabImage(1, 1, 32, 32); // dirt block
        block[1] = bs.grabImage(2, 1, 32, 32); // grass block
        block[2] = bs.grabImage(3, 1, 32, 32); // invis block
        
        player[0] = ps.grabImage(1, 1, 32, 64); // idle left player       
        player[1] = ps.grabImage(2, 1, 32, 64); // walking left player 1
        player[2] = ps.grabImage(3, 1, 32, 64); // walking left player 2
        player[3] = ps.grabImage(4, 1, 32, 64); // walking left player 3
        player[4] = ps.grabImage(5, 1, 32, 64); // walking left player 4
        player[5] = ps.grabImage(6, 1, 32, 64); // walking left player 5

        player[6] = ps.grabImage(1, 2, 32, 64); // idle right player
        player[7] = ps.grabImage(2, 2, 32, 64); // walking right player 1
        player[8] = ps.grabImage(3, 2, 32, 64); // walking right player 2
        player[9] = ps.grabImage(4, 2, 32, 64); // walking right player 3  
        player[10] = ps.grabImage(5, 2, 32, 64); // walking right player 4
        player[11] = ps.grabImage(6, 2, 32, 64); // walking right player 5
        
        player[12] = ps.grabImage(8, 1, 32, 64); // invln idle left player       
        player[13] = ps.grabImage(9, 1, 32, 64); // invln walking left player 1
        player[14] = ps.grabImage(10, 1, 32, 64); // invln walking left player 2
        player[15] = ps.grabImage(11, 1, 32, 64); // invln walking left player 3
        player[16] = ps.grabImage(12, 1, 32, 64); // invln walking left player 4
        player[17] = ps.grabImage(13, 1, 32, 64); // invln walking left player 5

        player[18] = ps.grabImage(8, 2, 32, 64); // invln idle right player
        player[19] = ps.grabImage(9, 2, 32, 64); // invln walking right player 1
        player[20] = ps.grabImage(10, 2, 32, 64); // invln walking right player 2
        player[21] = ps.grabImage(11, 2, 32, 64); // invln walking right player 3  
        player[22] = ps.grabImage(12, 2, 32, 64); // invln walking right player 4
        player[23] = ps.grabImage(13, 2, 32, 64); // invln walking right player 5
        
        enemy[0] = es.grabImage(1, 1, 32, 64); // croc enemy idle left
        enemy[1] = es.grabImage(1, 2, 32, 64); // croc enemy idle right
        
        enemy[2] = es.grabImage(1, 5, 32, 32); // bird enemy idle left
        enemy[3] = es.grabImage(1, 6, 32, 32); // bird enemy idle right
        
        enemy[4] = es.grabImage(1, 7, 32, 32); // rabbit enemy idle left
        enemy[5] = es.grabImage(1, 8, 32, 32); // rabbit enemy idle right
        
        projectile[0] = js.grabImage(1, 1, 32, 32); // player projectile left
        projectile[1] = js.grabImage(1, 2, 32, 32); // player projectile right
    }
    
    public BufferedImage getGameBackground() {
        return gameBackground;
    }
    
    public BufferedImage getMenuBackground(){
        return menuBackground;
    }
}
