/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import main.Game;
import world.Camera;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 *
 * @author samuel
 */
public class Player extends Entity {
    //Movimentação
    public boolean right, left;
    public static int vida = 100;
    public static int currentCoins = 0;
    public static int maxCoins = 0;
    private double gravity = 1;
    public int dir = 1;
    public boolean jump = false;
    public boolean isJumping = false;
    public int jumpHeight = 32;
    public int jumpFrames = 0;
    private int framesAnimation = 0;
    private int maxFrames = 15;
    private int maxSprite = 2;
    private int curSprite = 0;
    private int vspd;
    public Player(int x, int y, int width, int height,double speed,double gravity, BufferedImage sprite) {
        super(x, y, width, height,speed, sprite);
        this.gravity = gravity;
        depth=2;
    }
    public void tick() {
        if(World.isFree((int) x, (int) (y+gravity)) && isJumping == false){
            y+=gravity;
            for(int i = 0; i < Game.entities.size(); i++) {
                Entity e = Game.entities.get(i);
                if(e instanceof  Enemy) {
                    if (Entity.isColidding(this,e)) {
                        isJumping = true;
                        jumpHeight = 32;
                        ((Enemy) e).vida--;
                        if(((Enemy) e).vida == 0) {
                            Game.entities.remove(i);
                            break;
                        }
                    }
                }
            }
        }

        if(right && World.isFree((int) (x+speed), (int) y)) {
            dir = 1;
            x+=speed;
        } else if(left && World.isFree((int) (x-speed), (int) y)) {
            dir = -1;
            x-=speed;
        }
        if(jump) {
            if(!World.isFree(this.getX(),this.getY()+1)){
                isJumping = true;
            } else {
                jump= false;
            }
        }
        if(isJumping) {
            if(World.isFree(this.getX(),this.getY()-2)) {
                y-=2;
                jumpFrames+=2;
                if(jumpFrames == jumpHeight) {
                    isJumping = false;
                    jump = false;
                    jumpFrames = 0;
                }
            }else{
                isJumping =false;
                jump = false;
                jumpFrames = 0;
            }
        }
        for(int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if(e instanceof  Enemy) {
                if (Entity.isColidding(this,e)) {
                    if(Entity.rand.nextInt(100) < 5) vida--;
                }
            }
        }
        for(int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if(e instanceof  Moeda) {
                if (Entity.isColidding(this,e)) {
                    Game.entities.remove(i);
                    Player.currentCoins++;
                    break;
                }
            }
        }
        updateCamera();
    }
    public void render(Graphics g) {
        framesAnimation++;
        if(framesAnimation == maxFrames) {
            curSprite++;
            framesAnimation = 0;
            if(curSprite == maxSprite) {
                curSprite=0;
            }
        }
        if(dir == 1) {
            sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
        }else if(dir== -1) {
            sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
        }
        super.render(g);
    }
}
