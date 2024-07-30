package entities;

import main.Game;
import world.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{
    public int vida = 3;
    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    public void tick() {
        y+=speed;
        if(y >= Game.HEIGHT) {
            Game.entities.remove(this);
            Game.life-=Entity.rand.nextInt(9);
            return;
        }
        for(int i = 0; i < Game.entities.size();i++) {
            Entity e = Game.entities.get(i);
            if(e instanceof Bullet) {
                if (Entity.isColidding(this, e)) {
                    Game.entities.remove(i);
                    vida--;
                    if(vida <= 0) {
                        Explosion explosion = new Explosion(x,y,16,16,0,null);
                        Game.entities.add(explosion);
                        Game.score++;
                        Game.entities.remove(this);
                        return;
                    }
                    break;
                }
            }
        }
    }
}
