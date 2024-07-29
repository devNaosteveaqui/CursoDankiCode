/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author samuel
 */
public class FloorTile extends Tile {
    
    public FloorTile(int x, int y, BufferedImage sprite) {
        super(x, y, sprite);
    }
    public void render(Graphics g) {
        if(World.CICLO == World.dia) {
            g.drawImage(Tile.TILE_AR, x - Camera.x, y - Camera.y, null);
        } else if(World.CICLO == World.noite) {
            g.drawImage(Tile.TILE_NOITE, x - Camera.x, y - Camera.y, null);
        }
    }
}
