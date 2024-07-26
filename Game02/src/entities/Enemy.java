/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pacman.Game;
import pacman.world.AStar;
import pacman.world.Camera;
import pacman.world.Vector2i;

/**
 *
 * @author samuel
 */
public class Enemy extends Entity{
    
    public boolean ghostmode = false;
    public int ghostframes = 0;
    public int nexttime = Entity.rand.nextInt(60*2) + 60*3;
    public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
        super(x, y, width, height,speed,sprite);
    }
    @Override
    public void update() {
        if(!ghostmode) {
            if(path == null || path.size() == 0) {
                Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
                Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
                path = AStar.findPath(Game.world, start, end);
            }
            if(Game.rand.nextInt(100) < 80) {
                followPath(path);
            }
            /*if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 128) {
                if(Game.rand.nextInt(100) < 80) {
                    followPath(path);
                }
            }*/
            if(Game.rand.nextInt(100) < 5) {
                Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
                Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
                path = AStar.findPath(Game.world, start, end);
            }
        }
        ghostframes++;
        if(ghostframes == nexttime) {
            ghostframes = 0;
            if(ghostmode) {
                ghostmode = false;
            } else {
                ghostmode = true;
            }
        }
    }
    
    @Override
    public void render(Graphics g) {
        if(ghostmode) {
            g.drawImage(Entity.ENEMY_GHOST_SPRITE, this.getX()-Camera.x, this.getY()-Camera.y, null);
        } else {
            super.render(g);
        }
    }
}