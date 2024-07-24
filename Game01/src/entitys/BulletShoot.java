/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import game01.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import world.Camera;
import world.World;

/**
 *
 * @author samuel
 */
public class BulletShoot extends Entity{
    
    private double dx,dy;
    private double spd = 4;
    
    private int life = 10,curlife = 0;
    
    public BulletShoot(int x, int y, int width, int height, BufferedImage sprite,double dx, double dy) {
        super(x, y, width, height, sprite);
        this.dx = dx;
        this.dy = dy;
    }
    
    @Override
    public void update() {
        if(World.isFree((int)(x + dx*spd), (int)(y + dy*spd), 3, 3)) {
            x += dx*spd;
            y += dy*spd;
        }else {
            Game.bullets.remove(this);
            World.generateParticles(10,(int) x,(int) y);
        }
        curlife++;
        if(curlife > 10) {
            Game.bullets.remove(this);
        }
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(this.getX() - Camera.x,this.getY() - Camera.y,width, height);
    }
}
