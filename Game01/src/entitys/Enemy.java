/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import game01.Game;
import game01.Sound;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import world.AStar;
import world.Camera;
import world.Vector2i;
import world.World;

/**
 *
 * @author samuel
 */
public class Enemy extends Entity{
    private double speed = 0.6;
    
    //private int maskX=4,maskY=4,maskW=8,maskH=8;
    
    private int frames = 0,max_frames = 20, index = 0,max_index = 1;
    private BufferedImage[] sprites;
    
    private int life = 10;
    
    private boolean isDamage = false;
    private int damageFrames = 10,damageCurrent = 0;
    
    public Enemy(int x, int y, int width, int height, BufferedImage[] sprite) {
        super(x, y, width, height, null);
        sprites = new BufferedImage[2];
        sprites[0] = sprite[0];
        sprites[1] = sprite[1];
        this.setMask(4, 4, width, height);
    }
    @Override
    public void update() {
        /*if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 70) {
            if(!isColiddingPlayer()) {
                if (Game.rand.nextInt(100) < 50) {
                    if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY(),this.z) && !this.isColidding((int)(x+speed), this.getY())) {
                        x += speed;
                    } else if((int)x > Game.player.getX()&&World.isFree((int)(x-speed), this.getY(),this.z) && !this.isColidding((int)(x-speed), this.getY())) {
                        x -= speed;
                    }
                    if((int)y < Game.player.getY()&&World.isFree(this.getX(), (int) (y + speed),this.z) && !this.isColidding(this.getX(), (int) (y + speed))) {
                        y += speed;
                    } else if((int)y > Game.player.getY()&&World.isFree(this.getX(), (int)(y - speed),this.z) && !this.isColidding(this.getX(), (int)(y - speed))) {
                        y -= speed;
                    }
                }
            } else {
                if(Game.rand.nextInt() < 5) {
                    Sound.hurtEffect.play();
                    Game.player.vida -= Game.rand.nextInt(5);
                    Game.player.isDamaged = true;
                }
            }
        }*/
        if(!this.isColiddingPlayer()){
            if(path == null || path.size() == 0) {
                Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
                Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
                path = AStar.findPath(Game.world, start, end);
            }
        } else {
            if(Game.rand.nextInt() < 5) {
                //Sound.hurtEffect.play();
                Game.player.vida -= Game.rand.nextInt(5);
                Game.player.isDamaged = true;
            }
        }
        if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 70) {
            if(Game.rand.nextInt(100) < 30) {
                followPath(path);
            }
        }
        if(Game.rand.nextInt(100) < 5) {
            Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
            Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
            path = AStar.findPath(Game.world, start, end);
        }
        frames++;
        if(frames == max_frames) {
            frames = 0;
            index++;
            if(index > max_index) {
                index = 0;
            }
        }
        collidingBullet();
        if(life <= 0) {
            destroySelf();
        }

        if(isDamage) {
            this.damageCurrent++;
            if(damageCurrent >= damageFrames) {
                damageCurrent = 0;
                isDamage = false;
            }
        }
        
    }
    private void destroySelf() {
        Game.entities.remove(this);
        Game.enemys.remove(this);
    }
    private void collidingBullet() {
        for(int i = 0; i < Game.bullets.size(); i++) {
            Entity e = Game.bullets.get(i);
            if(e instanceof BulletShoot) {
                if(Entity.isColidding(this, e)) {
                    isDamage = true;
                    life--;
                    Game.bullets.remove(e);
                    return;
                }
            }
        }
    }
    public boolean isColiddingPlayer() {
        //Rectangle enemyCurrent = new Rectangle(this.getX() + maskX,this.getY() + maskY,maskW,maskH);
        //Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
        
        //return enemyCurrent.intersects(player);
        return isColidding(this,Game.player);
    }
    
    @Override
    public void render(Graphics g) {
        if(!isDamage){ 
            g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        } else {
            g.drawImage(Entity.ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }
}