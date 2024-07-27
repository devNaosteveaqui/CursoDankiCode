package entities;

import main.Game;
import world.AStar;
import world.Vector2i;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{
    public boolean right = true, left = false;
    public double vida = 30;
    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        path = AStar.findPath(Game.world,new Vector2i(this.getX()/16,this.getY()/16),new Vector2i(World.xFINAL,World.yFINAL));
    }
    public void tick() {
        followPath(path);

        if(x >= Game.WIDTH) {
            Game.VIDA -= Entity.rand.nextDouble();
            Game.entities.remove(this);
            return;
        }
        if(vida <= 0) {
            Game.entities.remove(this);
            Game.DINHEIRO++;
        }
    }
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.red);
        g.fillRect((int) x-7, (int) (y-5),30,2);
        g.setColor(Color.green);
        g.fillRect((int) x-7, (int) (y-5), (int) ((vida/30.0)*30),2);
        g.setColor(Color.white);
        g.drawRect((int) (x-7), (int) (y-5),30,2);
    }
}
