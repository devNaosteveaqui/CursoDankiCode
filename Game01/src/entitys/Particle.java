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

/**
 *
 * @author samuel
 */
public class Particle extends Entity{
    
    public int lifeTime;
    public int curLife;
    
    public int spd;
    public double dx,dy;
    
    public Particle(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        lifeTime = 10;
        curLife = 0;
        spd = 2;
        dx = Game.rand.nextGaussian();
        dy = Game.rand.nextGaussian();
    }
    
    @Override
    public void update() {
        x += dx*spd;
        y += dy*spd;
        curLife++;
        if(curLife == lifeTime) {
            Game.entities.remove(this);
        }
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
    }
    
}
