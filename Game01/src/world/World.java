/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import entitys.*;
import game01.Game;
import graficos.Spritesheet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author samuel
 */
public class World {
    private final static int WALL_TILE = 0xffffffff;
    private final static int FLOOR_TILE = 0xff000000;
    private final static int PLAYER_TILE = 0xff0000ff;
    private final static int ENEMY_TILE = 0xffff0000;
    private final static int LIFEPACK_TILE = 0xfffe00ff;
    private final static int WEAPON_TILE = 0xff808080;
    private final static int BULLET_TILE = 0xfffeff00;
    
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
                        tiles[i + (j*WIDTH)] = new FloorTile(i*16,j*16,Tile.TILE_FLOOR);
                    } else if(pixelAtual == WALL_TILE) {
                        tiles[i + (j*WIDTH)] = new WallTile(i*16,j*16,Tile.TILE_WALL);
                    } else if(pixelAtual == ENEMY_TILE) {
                        BufferedImage[] buff = new BufferedImage[2];
                        buff[0] = Entity.ENEMY_EN;
                        buff[1] = Entity.ENEMY_EN2;
                        Enemy e = new Enemy(i*16,j*16,16,16,buff);
                        Game.entities.add(e);
                        Game.enemys.add(e);
                    } else if(pixelAtual == PLAYER_TILE) {
                        Game.player.setX(i*16);
                        Game.player.setY(j*16);
                    } else  if(pixelAtual == BULLET_TILE) {
                        Game.entities.add(new Bullet(i*16,j*16,16,16,Entity.BULLET_EN));
                    } else  if(pixelAtual == WEAPON_TILE) {
                        Game.entities.add(new Weapon(i*16,j*16,16,16,Entity.WEAPON_EN));
                    } else  if(pixelAtual == LIFEPACK_TILE) {
                        LifePack pack = new LifePack(i*16,j*16,16,16,Entity.LIFEPACK_EN);
                        Game.entities.add(pack);
                    }else if (pixelAtual == 0xff00ff00){
                        tiles[i + (j*WIDTH)] = new FloorTile(i*16,j*16,Tile.TILE_FLOOR_FLOWER);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }/**/
        //Geração de Mapa Aleatório
        
        /*Game.player.setX(0);
        Game.player.setY(0);
        World.WIDTH = 100;
        World.HEIGHT = 100;
        World.tiles = new Tile[World.WIDTH*World.HEIGHT];
        
        for(int i = 0 ; i < World.WIDTH; i++) {
            for(int j = 0 ; j < World.HEIGHT; j++) {
                tiles[i+j*World.WIDTH] = new WallTile(i*16,j*16,Tile.TILE_WALL);
            }
        }
        
        int dir = 0;
        int xx = 0,yy = 0;
        
        for(int i = 0; i < 200; i++) {
            tiles[xx+yy*World.WIDTH] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
            if(dir == 0) {
                if(xx < World.WIDTH) {
                    xx++;
                }
            } else if(dir == 1) {
                if(xx > 0) {
                    xx--;
                }
            } else if(dir == 2) {
                if(yy < World.HEIGHT) {
                    yy++;
                }
            } else if(dir == 3) {
                if(yy > 0) {
                    yy--;
                }
            }
            if(Game.rand.nextInt(100) < 30) {
                dir = Game.rand.nextInt(3);
            }
        }*/
        
    }
    public static void generateParticles(int amount,int x, int y) {
        for(int i =0; i < amount; i++) {
            Game.entities.add(new Particle(x,y,1,1,null));
        }
    }
    public static boolean isFree(int xnext, int ynext,int width, int height) {
        int x1 = xnext / World.TILE_SIZE;
        int y1 = ynext / World.TILE_SIZE;
        
        int x2 = (xnext + width - 1) / World.TILE_SIZE;
        int y2 = (ynext) / World.TILE_SIZE;
        
        int x3 = xnext / World.TILE_SIZE;
        int y3 = (ynext + height - 1) / World.TILE_SIZE;
        
        int x4 = (xnext + width - 1) / World.TILE_SIZE;
        int y4 = (ynext + height - 1) / World.TILE_SIZE;
        
        if(!((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
                (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
                (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
                (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile))){
            return true;
        }
        return false;
    }
    public static boolean isFree(int xnext, int ynext,int zplayer) {
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
        if(zplayer > 0) {
            return true;
        }
        return false;
    }
    public static void restartGame(String lvl) {
        Game.enemys.clear();
        Game.entities.clear();
        Game.bullets.clear();
        Game.entities = new ArrayList<Entity>();
        Game.enemys = new ArrayList<Enemy>();
        Game.spritesheet = new Spritesheet("spritesheet.png");
        Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
        Game.entities.add(Game.player);
        Game.world = new World(lvl);
        return;
    }
    public void render(Graphics g) {
        int xstart = Camera.x >> 4;
        int ystart = Camera.y >> 4;
        
        int xfinal = xstart + (Game.WIDTH >> 4) + 1;
        int yfinal = ystart + (Game.HEIGHT >> 4) + 1;
        
        for(int i = xstart; i <= xfinal; i++) {
            for(int j = ystart; j <= yfinal; j++) {
                if(i < 0 || j < 0 || i >= WIDTH || j >= HEIGHT)
                    continue;
                Tile tile = tiles[i + (j*WIDTH)];
                tile.render(g);
            }
        }
    }
    public static void renderMiniMap() {
        for(int i =0; i < Game.minimappixels.length; i++) {
            Game.minimappixels[i] = 0xff000000;
        }
        for(int i =0; i < World.WIDTH; i++) {
            for(int j =0; j < World.HEIGHT; j++) {
                if(tiles[i + j*World.WIDTH] instanceof WallTile) {
                    Game.minimappixels[i + j*World.WIDTH] = 0xffff0000;
                }
            }
        }
        int xplayer = Game.player.getX()/16;
        int yplayer = Game.player.getY()/16;
        
        Game.minimappixels[xplayer + yplayer*World.WIDTH] = 0xff0000ff;
    }
}
