/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.world;

import pacman.Game;
import pacman.entities.Entity;
import pacman.entities.Cherry;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import static pacman.Game.player;
import static pacman.Game.spritesheet;
import pacman.entities.Enemy;
import pacman.entities.Player;

/**
 *
 * @author samuel
 */
public class World {
    private final static int WALL_TILE = 0xffffffff;
    private final static int FLOOR_TILE = 0xff000000;
    private final static int PLAYER_TILE = 0xff0000ff;
    private final static int ENEMY_TILE = 0xffff0000;
    private final static int CHERRY_TILE = 0xff00ff00;
    
    public static Tile[] tiles;
    public static int WIDTH,HEIGHT;
    public static final int TILE_SIZE = 16;

    
    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            
            int[] pixels = new int[map.getWidth()*map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth()*map.getHeight()];
            
            map.getRGB(0, 0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
            
            for(int i = 0; i < map.getWidth(); i++) {
                for(int j = 0; j < map.getHeight(); j++) {
                    
                    int pixelAtual = pixels[i + (j*map.getWidth())];
                    tiles[i + (j*WIDTH)] = new FloorTile(i*16,j*16,Tile.TILE_FLOOR);
                    if (pixelAtual == FLOOR_TILE) {
                        System.out.println("Chão");
                        tiles[i + (j*WIDTH)] = new FloorTile(i*16,j*16,Tile.TILE_FLOOR);
                    } else if(pixelAtual == WALL_TILE) {
                        System.out.println("Parede");
                        tiles[i + (j*WIDTH)] = new WallTile(i*16,j*16,Tile.TILE_WALL);
                    } else if(pixelAtual == PLAYER_TILE) {
                        Game.player.setX(i*16);
                        Game.player.setY(j*16);
                    } else if(pixelAtual == ENEMY_TILE) {
                        //Instanciar os inimigos e adicionar nas entities
                        Enemy enemy = new Enemy(i*16,j*16,16,16,1,Entity.ENEMY_SPRITE);
                        Game.entities.add(enemy);
                    } else if(pixelAtual == CHERRY_TILE) {
                        Cherry e = new Cherry(i*16,j*16,16,16,0,Entity.CHERRY_SPRITE);
                        Game.entities.add(e);
                        Game.frutas_contagem++;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
        
        if(!((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
                (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
                (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
                (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile))){
            return true;
        }
        return false;
    }
    public static void restartGame(String lvl) {
        Game.player = new Player(0,0,16,16,1,spritesheet.getSprite(32, 16, 16, 16));
        Game.entities.clear();
        Game.entities.add(player);
        Game.frutas_atual = 0;
        Game.frutas_contagem = 0;
        Game.world = new World("level1.png");
        return;
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
