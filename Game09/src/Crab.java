import java.awt.*;
import java.awt.image.BufferedImage;

public class Crab {
    public double x,y,dx,dy;
    public double spd = 4;
    public static BufferedImage[] crabSprite;
    public int curFrames =0,maxFrames = 10, maxAnim = 2,curAnim = 0;
    public Crab(int x, int y) {
        this.x = x;
        this.y = y;
        double radius = Math.atan2(Game.HEIGHT/2-20-y,Game.WIDTH/2-20-x);
        this.dx = Math.cos(radius);
        this.dy = Math.sin(radius);
        crabSprite = new BufferedImage[2];
        crabSprite[0] = Game.spritesheet.getSprite(0,0);
        crabSprite[1] = Game.spritesheet.getSprite(16,0);
    }
    public void update() {
        x+=(dx*spd);
        y+=(dy*spd);
        boolean colision = (new Rectangle((int) x, (int) y,40,40)).intersects(Game.maskHole);
        if(colision){
            Game.crabs.remove(this);
            return;
        }
        curFrames++;
        if(curFrames == maxFrames) {
            curAnim++;
            curFrames = 0;
            if(curAnim == maxAnim) {
                curAnim = 0;
            }
        }
        verificaColisao();
    }
    public void verificaColisao() {
        if(Game.isPressed) {
            Game.isPressed = false;
            if (Game.mx >= x && Game.mx <= x + 40) {
                if (Game.my >= y && Game.my <= y + 40) {
                    Game.crabs.remove(this);
                    Game.smokes.add(new Smoke((int) x, (int) y));
                    Game.score++;
                    return;
                }
            }
        }
    }
    public void render(Graphics g) {
        //g.setColor(Color.red);
        //g.fillRect((int) x, (int) y,40,40);
        g.drawImage((Image) crabSprite[curAnim], (int) x, (int) y,40,40,null );
    }
}
