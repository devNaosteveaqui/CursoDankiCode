/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import com.sun.java.swing.plaf.windows.WindowsIconFactory;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import graphics.UI;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 *
 * @author samuel
 */
public class World {
    private final static int WALL_TILE = 0xffffffff;
    private final static int FLOOR_TILE = 0xff000000;
    private final static int PLAYER_TILE = 0xff0026ff;
    private final static int ENEMY_TILE = 0xffff0000;
    private final static int CHERRY_TILE = 0xff00ff00;
    
    public static Tile[] tiles;
    public static int WIDTH,HEIGHT;
    public static final int TILE_SIZE = 16;
    public static int dia = 0;
    public static int noite = 1;
    public static int CICLO = Entity.rand.nextInt(2);
    
    public World() {
        String[] tilesTypes = {"grama","terra","areia","neve"};
        WIDTH = 200;
        HEIGHT = 80;//Game.HEIGHT/16;
        //Divisor do mapa
        int divisao = WIDTH/tilesTypes.length;
        tiles = new Tile[(WIDTH)*(HEIGHT)];
        for(int xx = 0; xx<WIDTH; xx++){
            int initialHeight = Entity.rand.nextInt(12-8)+8;
            for(int yy =0; yy< HEIGHT; yy++){
                if(yy == HEIGHT - 1 || xx == (WIDTH) || xx == 0 || yy == 0) {
                    tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_SOLID);
                    tiles[xx+yy*WIDTH].solid = true;
                } else {
                    if(yy >= initialHeight) {
                        int indexBioma = xx/divisao;
                        if(tilesTypes[indexBioma]=="grama") {
                            tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_GRAMA);
                        } else if(tilesTypes[indexBioma] =="terra"){
                            tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TERRA);
                        } else if(tilesTypes[indexBioma] =="neve"){
                            tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_NEVE);
                        } else if(tilesTypes[indexBioma] =="areia"){
                            tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_AREIA);
                        }
                    } else {
                        tiles[xx + yy * WIDTH] = new FloorTile(xx * 16, yy * 16, Tile.TILE_AR);
                    }
                }
            }
        }
    }
    public static boolean isFree(int xnext, int ynext) {
        int x1 = xnext / TILE_SIZE;
        int y1 = ynext / TILE_SIZE;
        
        int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = (ynext) / TILE_SIZE;
        
        int x3 = xnext / TILE_SIZE;
        int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;
        
        int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;
        
        if(!(
                (tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
                (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
                (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
                (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)
        )){
            return true;
        }
        return false;
    }
    public static void restartGame() {
        //TODO: Aplicar método para reiniciar o jogo corretamente
        return;
    }
    public void tick() {

    }
    public void render(Graphics g) {
        int xstart = Camera.x >> 4;
        int ystart = Camera.y >> 4;
        
        int xfinal = xstart + (Game.WIDTH >> 4);
        int yfinal = ystart + (Game.HEIGHT >> 4);
        
        for(int i = xstart; i <= xfinal; i++) {
            for(int j = ystart; j <= yfinal; j++) {
                if(i < 0 || j < 0 || i >= WIDTH || j >= HEIGHT)
                    continue;
                Tile tile = tiles[i + (j*WIDTH)];
                tile.render(g);
            }
        }
    }
}
