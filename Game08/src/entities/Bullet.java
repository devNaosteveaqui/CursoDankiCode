package entities;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Entity{
    public Bullet(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }
    public void tick() {
        y-=speed;
        if(y<0) {
            Game.entities.remove(this);
            return;
        }
    }
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(this.getX(),this.getY(),width,height);
    }
}
