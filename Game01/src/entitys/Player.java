/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import game01.Game;
import graficos.Spritesheet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import world.Camera;
import world.World;

/**
 *
 * @author samuel
 */
public class Player extends Entity {
    //Movimentação
    public boolean right,up,left,down;
    public int right_dir = 0,left_dir = 1;
    public int dir = right_dir;
    public double speed = 1.0;
    //Animação
    private int frames = 0,max_frames = 5, index = 0,max_index = 3;
    private boolean moved = false;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;
    //Animação - Feedback
    private BufferedImage playerdameged;
    public boolean isDamaged = false;
    private int damageFrame = 0;
    //Sistema de tiro
    private boolean hasGun;
    public int ammo = 0;
    public boolean shoot = false,mouseshoot = false;
    //Atributos de vida do personagem
    public double vida = 100,vidamax = 100;
    //Mira da arma com mouse
    public int mx,my;
    //Fake Jump
    public boolean jump = false,isJumping = false, jumpUp = false,jumpDown = false;
    //public int z = 0;
    public int jumpFrames = 50,jumpCur = 0;
    private int jumpspd = 2;
    
    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        
        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];
        
        playerdameged = Game.spritesheet.getSprite(0, 16, 16, 16);
        for(int i = 0; i<4; i++) {
            rightPlayer[i] = Game.spritesheet.getSprite(32 + i*16, 0, 16, 16);
        }
        for(int i = 0; i<4; i++) {
            leftPlayer[i] = Game.spritesheet.getSprite(32 + i*16, 16, 16, 16);
        }
    }
    public void revealMap() {
        int xx = (int) (x/16);
        int yy = (int) (y/16);
        
        World.tiles[xx+(yy)*World.WIDTH].show = true;
        World.tiles[xx-1+(yy)*World.WIDTH].show = true;
        World.tiles[xx+1+(yy)*World.WIDTH].show = true;
        
        World.tiles[xx+(yy+1)*World.WIDTH].show = true;
        World.tiles[xx+(yy-1)*World.WIDTH].show = true;
        
        World.tiles[xx+1+(yy+1)*World.WIDTH].show = true;
        World.tiles[xx+1+(yy-1)*World.WIDTH].show = true;
        
        World.tiles[xx-1+(yy+1)*World.WIDTH].show = true;
        World.tiles[xx-1+(yy-1)*World.WIDTH].show = true;
    }
    @Override
    public void update() {
        revealMap();
        //Move
        depth = 1;
        moved = false;
        if(right && World.isFree((int) (x+speed),this.getY(),this.z)) {
            moved = true;
            dir = right_dir;
            x += speed;
        } else if(left && World.isFree((int)(x-speed),this.getY(),this.z)) {
            moved = true;
            dir = left_dir;
            x -= speed;
        }
        if(up && World.isFree(this.getX(), (int) (y-speed),this.z)) {
            moved = true;
            y -= speed;
        } else if(down && World.isFree(this.getX(), (int) (y+speed),this.z)) {
            moved = true;
            y += speed;
        }
        if(moved) {
            frames++;
            if(frames == max_frames) {
                frames = 0;
                index++;
                if(index > max_index) {
                    index = 0;
                }
            }
        }
        //Fake jump
        if(jump) {
            if(!isJumping) {
                jump = false;
                isJumping = true;
                jumpUp = true;
            }
        }
        if(isJumping) {
            if(jumpUp){
                jumpCur += jumpspd;
            } else if(jumpDown) {
                jumpCur -= jumpspd;
                if(jumpCur <= 0) {
                    isJumping = false;
                    jumpDown = false;
                    jumpUp = false;
                }
            }
            z = jumpCur;
            if(jumpCur >= jumpFrames) {
                jumpUp=false;
                jumpDown = true;
            }
        }
        //Colision
        checkColisionLifePack();
        checkColisionAmmo();
        checkColisionGun();
        //Animação Feedback
        if(isDamaged) {
            this.damageFrame++;
            if(this.damageFrame == 4) {
                this.damageFrame = 0;
                this.isDamaged = false;
            }
        }
        //Shoot system
        if(shoot) {
            shoot = false;
            if(hasGun && ammo > 0) {
                ammo--;
                int dx;
                int dy = 0;
                int px = 0;
                int py = 8;
                if(dir == right_dir) {
                    dx = 1;
                    px = 12;
                } else {
                    px = 1;
                    dx = -1;
                }
                BulletShoot bullet = new BulletShoot(this.getX() + px,this.getY() + py,3,3,null,dx,dy);
                Game.bullets.add(bullet);
            }
        }
        //Mouse Shoot System
        if(mouseshoot) {
            mouseshoot = false;
            if(hasGun && ammo > 0) {
                ammo--;
                double angle = 0;
                int px = 8,py = 8;
                
                if(dir == right_dir) {
                    px = 12;
                    angle = Math.atan2(my - (this.getY() + py - Camera.y),mx - (this.getX() + px - Camera.x));
                } else {
                    px = 1;
                    angle = Math.atan2(my - (this.getY() + py - Camera.y),mx - (this.getX() + px - Camera.x));
                }
                
                double dx = Math.cos(angle);
                double dy = Math.sin(angle);
                
                BulletShoot bullet = new BulletShoot(this.getX() + px,this.getY() + py,3,3,null, dx, dy);
                Game.bullets.add(bullet);
            }
        }
        //Game Over Verification
        if(vida <= 0) {
            vida = 0;
            Game.gameState = Game.GAME_OVER;
        }
        
        updateCamera();
    }
    public void updateCamera() {
        Camera.x = Camera.clamp(this.getX() - Game.WIDTH/2, 0, World.WIDTH*16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - Game.HEIGHT/2, 0, World.HEIGHT*16 - Game.HEIGHT);
    }
    public void checkColisionGun() {
        for(int i = 0; i < Game.entities.size(); i ++) {
            Entity e = Game.entities.get(i);
            if(e instanceof Weapon) {
                if(Entity.isColidding(this, e)) {
                    hasGun = true;
                    Game.entities.remove(i);
                    return;
                }
            }
        }
    }
    public void checkColisionAmmo() {
        for(int i = 0; i < Game.entities.size(); i ++) {
            Entity e = Game.entities.get(i);
            if(e instanceof Bullet) {
                if(Entity.isColidding(this, e)) {
                    ammo +=20;
                    Game.entities.remove(i);
                    return;
                }
            }
        }
    }
    public void checkColisionLifePack() {
        for(int i = 0; i < Game.entities.size(); i ++) {
            Entity e = Game.entities.get(i);
            if(e instanceof LifePack) {
                if(Entity.isColidding(this, e)) {
                    vida += 10;
                    if(vida >= 100) {
                        vida = 100;
                    }
                    Game.entities.remove(i);
                    return;
                }
            }
        }
        
    }
    @Override
    public void render(Graphics g) {
        if(!isDamaged) {
            //g.drawRect(this.getX()-Camera.x, this.getY() - Camera.y - z, this.width, this.height);
            if(dir == right_dir) {
                g.drawImage(rightPlayer[index], this.getX()-Camera.x, this.getY()-Camera.y -z, null);
                if(hasGun) {
                    g.drawImage(Entity.GUN_RIGHT, this.getX()-Camera.x, this.getY()-Camera.y+2-z, null);
                }
            } else if(dir == left_dir) {
                g.drawImage(leftPlayer[index], this.getX()-Camera.x, this.getY()-Camera.y-z, null);
                if(hasGun) {
                    g.drawImage(Entity.GUN_LEFT, this.getX()-Camera.x, this.getY()-Camera.y + 2-z, null);
                }
            }
        } else {
            g.drawImage(playerdameged, this.getX()-Camera.x, this.getY()-Camera.y-z, null);
        }
        if(isJumping) {
            g.setColor(Color.black);
            g.fillOval(this.getX()-Camera.x, this.getY()-Camera.y+8, 16, 8);
        }
    }
}
