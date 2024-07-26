/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.entities;

import pacman.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pacman.world.Camera;
import pacman.world.World;

/**
 *
 * @author samuel
 */
public class Player extends Entity {
    //Movimentação
    public boolean right,up,left,down;
    
    public BufferedImage sprite_left;
    
    public int lastDir = 1;
    
    public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
        super(x, y, width, height,speed, sprite);
        sprite_left = Game.spritesheet.getSprite(48, 16, 16, 16);
    }
    @Override
    public void update() {
        //Move
        depth = 1;
        if(right && World.isFree((int) (x+speed),this.getY())) {
            x += speed;
            lastDir = 1;
        } else if(left && World.isFree((int)(x-speed),this.getY())) {
            x -= speed;
            lastDir = -1;
        }
        if(up && World.isFree(this.getX(), (int) (y-speed))) {
            y -= speed;
        } else if(down && World.isFree(this.getX(), (int) (y+speed))) {
            y += speed;
        }
        
        verificarPegaFruta();
        
        if(Game.frutas_atual == Game.frutas_contagem) {
            System.out.println("End Game!\nYou Win!");
            World.restartGame("");
        }
    }
    public void verificarPegaFruta() {
        for(int i =0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if(current instanceof Cherry) {
                if(Entity.isColidding(this,current)) {
                    Game.entities.remove(i);
                    Game.frutas_atual++;
                    return;
                }
            }
        }
    }
    @Override
    public void render(Graphics g) {
        if(lastDir == 1) {
            super.render(g);
            
        } else {
            g.drawImage(sprite_left, this.getX()-Camera.x, this.getY()-Camera.y, null);
        }
    }
}
