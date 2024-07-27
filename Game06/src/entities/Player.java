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
    public int xTarget, yTarget;
    public boolean atacando = false;
    public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
        super(x, y, width, height,speed, sprite);
        depth=2;
    }
    public void tick() {
        Enemy enemy = null;
        for(int i =0; i<Game.entities.size();i++) {
            Entity e = Game.entities.get(i);
            if(e instanceof Enemy) {
                int xEnemy = e.getX();
                int yEnemy = e.getY();
                if(Entity.calculateDistance(this.getX(),this.getY(),xEnemy,yEnemy) < 40) {
                    enemy = (Enemy) e;
                }
            }
        }
        if(enemy != null) {
            atacando = true;
            xTarget = enemy.getX();
            yTarget = enemy.getY();
            if(Entity.rand.nextInt(100)<50)
                enemy.vida -=Entity.rand.nextDouble();
        } else {
            atacando = false;
        }
    }
    public void render(Graphics g){
        super.render(g);
        if(atacando) {
            g.setColor(Color.red);
            g.drawLine((int) x+8, (int) y+8,xTarget+8,yTarget+8);
        }
    }

}
