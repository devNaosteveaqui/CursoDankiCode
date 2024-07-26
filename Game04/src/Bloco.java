import java.awt.*;

public class Bloco extends Rectangle {
    public Bloco(int x, int y ) {
        super(x,y,32,32);
    }
    public void render(Graphics g){
        g.drawImage(Spritesheet.tile_wall, x,y,32,32,null);
    }
}
