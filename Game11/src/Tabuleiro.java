import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {
    public static final int WIDTH = 6, HEIGHT = 6;
    public static final int GRID_SIZE = 40;
    public static int[][] TABULEIRO;
    public static int DOCE_0 = 0, DOCE_1 = 1, DOCE_2 = 2;
    public static BufferedImage spritesheet;
    public BufferedImage[] DOCE_SPRITE = new BufferedImage[3];
    public Tabuleiro() {
        TABULEIRO = new int[WIDTH][HEIGHT];
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                TABULEIRO[x][y]= new Random().nextInt(3);
            }
        }
        DOCE_SPRITE[0] = getSprite(272,919,153,134);
        DOCE_SPRITE[1] = getSprite(425,919,153,134);
        DOCE_SPRITE[2] = getSprite(272,1072,153,134);
    }
    public void update() {
        ArrayList<Candy> combos = new ArrayList<Candy>();
        for(int yy = 0; yy < HEIGHT; yy++) {
            if(combos.size() == 3) {
                for(int i = 0; i < combos.size(); i++) {
                    int xtemp = combos.get(i).x;
                    int ytemp = combos.get(i).y;

                    TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
                }
                combos.clear();
                Game.points++;
                Game.frame.setTitle("Candy Crush - pontos : " + Game.points);
                return;
            }
            combos.clear();
            for(int xx = 0; xx < WIDTH; xx++) {
                int cor = TABULEIRO[xx][yy];
                if(combos.size() == 3) {
                    for(int i = 0; i < combos.size(); i++) {
                        int xtemp = combos.get(i).x;
                        int ytemp = combos.get(i).y;

                        TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
                    }
                    combos.clear();
                    Game.points++;
                    Game.frame.setTitle("Candy Crush - pontos : " + Game.points);
                    return;
                }
                if(combos.size() == 0) {
                    combos.add(new Candy(xx,yy,cor));
                } else if(combos.size() > 0) {
                    if(combos.get(combos.size() - 1).CANDY_TYPE == cor) {
                        combos.add(new Candy(xx,yy,cor));
                    } else {
                        combos.clear();
                        combos.add(new Candy(xx,yy,cor));
                    }
                }
            }
        }
        combos = new ArrayList<Candy>();
        for(int xx = 0; xx < WIDTH; xx++) {
            if(combos.size() == 3) {
                for(int i = 0; i < combos.size(); i++) {
                    int xtemp = combos.get(i).x;
                    int ytemp = combos.get(i).y;

                    TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
                }
                combos.clear();
                Game.points++;
                Game.frame.setTitle("Candy Crush - pontos : " + Game.points);
                return;
            }
            combos.clear();
            for(int yy = 0; yy < HEIGHT; yy++) {
                int cor = TABULEIRO[xx][yy];
                if(combos.size() == 3) {
                    for(int i = 0; i < combos.size(); i++) {
                        int xtemp = combos.get(i).x;
                        int ytemp = combos.get(i).y;

                        TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
                    }
                    combos.clear();
                    Game.points++;
                    Game.frame.setTitle("Candy Crush - pontos : " + Game.points);
                    return;
                }
                if(combos.size() == 0) {
                    combos.add(new Candy(xx,yy,cor));
                } else if(combos.size() > 0) {
                    if(combos.get(combos.size() - 1).CANDY_TYPE == cor) {
                        combos.add(new Candy(xx,yy,cor));
                    } else {
                        combos.clear();
                        combos.add(new Candy(xx,yy,cor));
                    }
                }
            }
        }
    }
    public static BufferedImage getSprite(int x, int y, int width, int height) {
        if(spritesheet == null) {
            try {
                spritesheet = ImageIO.read(Tabuleiro.class.getResource("/spritesheet.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return spritesheet.getSubimage(x,y,width,height);
    }
    public void render(Graphics g) {
        int offset_x = (Game.WIDTH - WIDTH*GRID_SIZE)/2;
        int offset_y = (Game.HEIGHT - HEIGHT*GRID_SIZE)/2;
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                int px = x*GRID_SIZE + offset_x;
                int py = y*GRID_SIZE + offset_y;
                g.setColor(Color.WHITE);
                g.drawRect(px,py,GRID_SIZE,GRID_SIZE);
                int doce = TABULEIRO[x][y];
                if(doce == DOCE_0) {
                    //g.setColor(Color.red);
                    //g.fillRect(px+(GRID_SIZE-25)/2,py+(GRID_SIZE-25)/2,25,25);
                    g.drawImage(DOCE_SPRITE[doce],px+(GRID_SIZE-25)/2,py+(GRID_SIZE-25)/2,25,25,null);
                }
                if(doce == DOCE_1) {
                    //g.setColor(Color.green);
                    //g.fillRect(px+(GRID_SIZE-25)/2,py+(GRID_SIZE-25)/2,25,25);
                    g.drawImage(DOCE_SPRITE[doce],px+(GRID_SIZE-25)/2,py+(GRID_SIZE-25)/2,25,25,null);
                }
                if(doce == DOCE_2) {
                    //g.setColor(Color.yellow);
                    //g.fillRect(px+(GRID_SIZE-25)/2,py+(GRID_SIZE-25)/2,25,25);
                    g.drawImage(DOCE_SPRITE[doce],px+(GRID_SIZE-25)/2,py+(GRID_SIZE-25)/2,25,25,null);
                }
                if(Game.selected) {
                    int posx = Game.previousx/GRID_SIZE;
                    int posy = Game.previousy/GRID_SIZE;
                    g.setColor(Color.BLACK);
                    g.drawRect(posx*GRID_SIZE + offset_x,posy*GRID_SIZE + offset_y,GRID_SIZE,GRID_SIZE);
                }
            }
        }
    }

}
