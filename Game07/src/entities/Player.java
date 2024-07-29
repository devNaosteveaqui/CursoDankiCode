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
    public double vida = 100.0;

    private double gravity = 3;
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
    public BufferedImage ATTACK_RIGHT;
    public BufferedImage ATTACK_LEFT;
    public boolean attack = false;
    public boolean isAttacking = false;
    public int attackFrames = 0;
    public int maxFramesAttack = 10;
    public Player(int x, int y, int width, int height,double speed,double gravity, BufferedImage sprite) {
        super(x, y, width, height,speed, sprite);
        this.gravity = gravity;
        depth=2;
        ATTACK_RIGHT = Game.spritesheet.getSprite(64,0,16,16);
        ATTACK_LEFT = Game.spritesheet.getSprite(80,0,16,16);
    }
    public void tick() {
        if(World.isFree((int) x, (int) (y+gravity)) && isJumping == false){
            y+=gravity;
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
        if(attack) {
            if(isAttacking == false) {
                attack = false;
                isAttacking = true;
            }
        }
        if(isAttacking) {
            attackFrames++;
            if(attackFrames==maxFramesAttack) {
                attackFrames =0;
                isAttacking = false;
            }
        }
        collisionEnemy();
        updateCamera();
    }
    public void collisionEnemy() {
        for(int i = 0;i<Game.entities.size();i++) {
            Entity e = Game.entities.get(i);
            if(e instanceof Enemy) {
                if(Entity.rand.nextInt(100)<30) {
                    if (Entity.isColidding(this, e)) {
                        vida -= 0.3;
                        if(isAttacking) {
                            ((Enemy) e).vida--;
                        }
                    }
                }
            }
        }
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
            if(isAttacking) {
                g.drawImage(ATTACK_RIGHT,this.getX()+8-Camera.x,this.getY()-Camera.y,null);
            }
        }else if(dir== -1) {
            sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
            if(isAttacking) {
                g.drawImage(ATTACK_LEFT, this.getX()-16-Camera.x,this.getY()-Camera.y, null);
            }
        }
        super.render(g);
    }
}
