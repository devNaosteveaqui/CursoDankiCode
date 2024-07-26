import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class World {
    public static List<Bloco> blocos = new ArrayList<Bloco>();
    public World() {
        for(int xx =0; xx<15*2;xx++) {
            blocos.add(new Bloco(xx*32,0));
        }
        for(int xx =0; xx<15*2;xx++) {
            blocos.add(new Bloco(xx*32,Game.HEIGHT-32));
        }
        for(int yy =0; yy<15*2;yy++) {
            blocos.add(new Bloco(0,yy*32));
        }
        for(int yy =0; yy<15*2;yy++) {
            blocos.add(new Bloco(Game.WIDTH-32,yy*32));
        }
    }
    public static boolean isFree(int x, int y) {
        for(int i = 0; i < blocos.size(); i++) {
            Bloco blocoatual = blocos.get(i);
            if(blocoatual.intersects(new Rectangle(x,y,32,32))) {
                return false;
            }
        }
        return true;
    }
    public void render(Graphics g) {
        for(int i = 0; i < blocos.size(); i++) {
            blocos.get(i).render(g);
        }
    }
}
