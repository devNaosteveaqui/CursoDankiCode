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
    public boolean right, left;
    public boolean isShooting = false;

    public Player(int x, int y, int width, int height,double speed,double gravity, BufferedImage sprite) {
        super(x, y, width, height,speed, sprite);
    }
    public void tick() {
        if(right) {
            x+=speed;
        } else if(left) {
            x-=speed;
        }
        if(x>=Game.WIDTH) {
            x = -16;
        } else if(x+16<0) {
            x = Game.WIDTH;
        }
        if(isShooting) {
            isShooting = false;
            int xx = this.getX() + 5;
            int yy = this.getY();

            Bullet bullet = new Bullet(xx,yy,3,3,1,null);
            Game.entities.add(bullet);
        }
    }
}
