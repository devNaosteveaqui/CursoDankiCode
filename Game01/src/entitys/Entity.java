/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import game01.Game;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import world.Camera;
import world.Node;
import world.Vector2i;

/**
 *
 * @author samuel
 */
public class Entity {
    public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(96, 0, 16, 16);
    public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(112, 0, 16, 16);
    public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(96, 16, 16, 16);
    
    public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(112, 16, 16, 16);
    public static BufferedImage ENEMY_EN2 = Game.spritesheet.getSprite(128, 16, 16, 16);
    public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(144, 16, 16, 16);
    
    public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(144, 0, 16, 16);
    public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(128, 0, 16, 16);
    
    public double x;
    public double y;
    protected int z;
    protected int width;
    protected int height;
    private int maskx,masky,mWidth,mHeight;
    
    public int depth;
    
    private BufferedImage sprite;
    
    protected List<Node> path;
    public static Comparator<Entity> nodeSorter = new Comparator<Entity>(){
        @Override
        public int compare(Entity e0, Entity e1) {
            if(e1.depth < e0.depth) {
                return +1;
            }
            if(e1.depth > e0.depth) {
                return -1;
            }
            return 0;
        }
    };
    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        
        this.maskx = 0;
        this.masky = 0;
        this.mWidth = width;
        this.mHeight = height;
        depth = 0;
    }
    public void setMask(int maskx,int masky,int mWidth,int mHeight) {
        this.maskx = maskx;
        this.masky = masky;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
    }
    public void setX(int newX) {
        this.x = newX;
    }
    public void setY(int newY) {
        this.y = newY;
    }
    public int getX() {
        return (int) this.x;
    }
    public int getY() {
        return (int) this.y;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public void update() {}
    
    public double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    }
    
    public void followPath(List<Node> path) {
        if(path != null) {
            if(path.size() > 0) {
                Vector2i target = path.get(path.size()-1).tile;
                //xprev = x;
                //yprev = y;
                if(x < target.x*16 && !this.isColidding(this.getX()+1, this.getY())) {
                    x++;
                } else if(x > target.x*16 && !this.isColidding(this.getX()-1, this.getY())) {
                    x--;
                }
                
                if(y < target.y*16 && !this.isColidding(this.getX(), this.getY()+1)){
                    y++;
                } else if(y > target.y*16 && !this.isColidding(this.getX(), this.getY()-1)) {
                    y--;
                }
                
                if(x == target.x*16 && y == target.y*16) {
                    path.remove(path.size()-1);
                }
            }
        }
    }
    
    public void render(Graphics g) {
        g.drawImage(sprite, this.getX()-Camera.x, this.getY()-Camera.y, null);
    }
    public static boolean isColidding(Entity e1, Entity e2) {
        Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY() + e1.masky,e1.mWidth,e1.mHeight);
        Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY() + e2.masky,e2.mWidth,e2.mHeight);
        
        return (e1Mask.intersects(e2Mask) && e1.z == e2.z);
    }
    public boolean isColidding(int xnext, int ynext) {
        Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,mWidth,mHeight);
        for(int i = 0; i < Game.enemys.size(); i++) {
            Enemy e = Game.enemys.get(i);
            if(e == this)
                continue;
            Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY() + masky,mWidth,mHeight);
            if(enemyCurrent.intersects(targetEnemy)) {
                return true;
            }
        }
        return false;
    }
}
