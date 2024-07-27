package entities;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spawner extends Entity{
    private int timer = 60;
    private int curTimer = 0;
    public Spawner(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    public void tick() {
        curTimer++;
        if(curTimer == timer) {
            curTimer=0;
            timer = Entity.rand.nextInt(60-30) + 30;
            Enemy enemy = new Enemy(x,y,16,16,Entity.rand.nextDouble()+Entity.rand.nextInt(2), Entity.ENEMY_RIGHT);
            Game.entities.add(enemy);
        }
    }
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y,width,height);
    }
}
