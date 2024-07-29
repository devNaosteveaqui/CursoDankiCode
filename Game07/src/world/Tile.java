/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author samuel
 */
public class Tile {
    
    public static BufferedImage TILE_GRAMA = Game.spritesheet.getSprite(16, 0, 16, 16);
    public static BufferedImage TILE_TERRA = Game.spritesheet.getSprite(16, 16, 16, 16);
    public static BufferedImage TILE_AREIA = Game.spritesheet.getSprite(32, 0, 16, 16);
    public static BufferedImage TILE_NEVE = Game.spritesheet.getSprite(48, 0, 16, 16);
    public static BufferedImage TILE_AR = Game.spritesheet.getSprite(0, 0, 16, 16);
    public static BufferedImage TILE_NOITE = Game.spritesheet.getSprite(48, 32, 16, 16);
    public static BufferedImage TILE_SOLID = Game.spritesheet.getSprite(32,32,16,16);
    public boolean solid = false;
    private BufferedImage sprite;
    public int x,y;
    
    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }
    
    public void render(Graphics g) {
        g.drawImage(sprite, x-Camera.x, y-Camera.y, null);
    }
}
