package entities;

import main.Game03_FlappyBird;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tubo extends Entity{
    public Tubo(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    public void tick() {
        x--;
        if(x+width <= 0) {
            Game03_FlappyBird.score++;
            Game03_FlappyBird.entities.remove(this);
            return;
        }
    }
    public void render(Graphics g) {
        if(sprite != null) {
            g.drawImage(sprite,this.getX(),this.getY(),width,height,null);
        } else {
            g.setColor(Color.green);
            g.fillRect((int) x, (int) y,width,height);
        }
    }
}
