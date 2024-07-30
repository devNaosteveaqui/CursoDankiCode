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
