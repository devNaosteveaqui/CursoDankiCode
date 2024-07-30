package entities;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends Entity {
    private int frames = 0;
    private int targetFrames = 10;
    private int maxAnimation = 2;
    private int curAnimation = 0;
    public BufferedImage[] explosionSprites = new BufferedImage[3];
    public Explosion(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        explosionSprites[0] = Game.spritesheet.getSprite(16,0,16,16);
        explosionSprites[1] = Game.spritesheet.getSprite(32,0,16,16);
        explosionSprites[2] = Game.spritesheet.getSprite(48,0,16,16);
    }
    public void tick() {
        frames++;
        if(frames == targetFrames) {
            frames = 0;
            curAnimation++;
            if(curAnimation>maxAnimation) {
                Game.entities.remove(this);
                return;
            }
        }
    }
    public void render(Graphics g){
        g.drawImage(explosionSprites[curAnimation],this.getX(),this.getY(),null);
    }
}
