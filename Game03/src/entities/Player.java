/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import main.Game03_FlappyBird;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 *
 * @author samuel
 */
public class Player extends Entity {
    //Movimentação
    
    public boolean isPresssed = false;
    
    public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
        super(x, y, width, height,speed, sprite);
        depth=2;
    }
    public void tick() {
        if(isPresssed) {
            if(y > 0) {
                y-=2;
            }
        } else {
            y+=2;
        }
        if(y> Game03_FlappyBird.HEIGHT) {
            World.restartGame();
            return;
        }
        for(int i =0; i <Game03_FlappyBird.entities.size(); i++) {
            Entity e = Game03_FlappyBird.entities.get(i);
            if(e!=this) {
                if(Entity.isColidding(this,e)){
                    World.restartGame();
                    return;
                }
            }
        }
    }
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(!isPresssed) {
            g2.rotate(20,x+width/2,y+height/2);
            super.render(g);
            //g2.rotate(-20,x+width/2,y+height/2);
        } else {
            g2.rotate(-20,x+width/2,y+height/2);
            super.render(g);
            //g2.rotate(20,x+width/2,y+height/2);
        }

    }

}
