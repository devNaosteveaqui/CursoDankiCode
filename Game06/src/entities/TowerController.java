package entities;

import main.Game;
import sun.security.acl.WorldGroupImpl;
import world.World;

import java.awt.image.BufferedImage;

public class TowerController extends Entity{
    public boolean isPressed = false;
    public int xTarget,yTarget;

    public TowerController(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }
    public void tick() {
        //System.out.println(isPressed);
        if(isPressed) {
            isPressed = false;
            boolean liberado = true;
            int xx = (xTarget/16)*16;
            int yy = (yTarget/16)*16;
            Player player = new Player(xx,yy,16,16,0, Game.spritesheet.getSprite(16,16,16,16));

            for(int i =0; i<Game.entities.size();i++) {
                Entity e = Game.entities.get(i);
                if(e instanceof Player) {
                    if(Entity.isColidding(e,player)){
                        liberado = false;
                    }
                }
            }

            if(World.isFree(xx,yy)) {
                liberado = false;
            }

            if(liberado) {
                if(Game.DINHEIRO >= 2){
                    Game.entities.add(player);
                    Game.DINHEIRO -= 2;
                }
            }

        }
        if(Game.VIDA <= 0) {
            System.exit(1);
        }
    }
}
