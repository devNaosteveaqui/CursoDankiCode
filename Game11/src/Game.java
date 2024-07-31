import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, MouseMotionListener,MouseListener {
    public static final int WIDTH = 288, HEIGHT = 288;
    public static final int SCALE = 2;
    public static final int FPS = 1000/60;
    public BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    public Tabuleiro tabuleiro;
    public static JFrame frame;
    public static int points = 0;
    public static boolean selected = false;
    public static int previousx = 0, previousy = 0;
    public static int nextx = -1, nexty = -1;
    public Game() {
        this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        tabuleiro = new Tabuleiro();
    }

    public static void main(String[] args) {
        Game.frame = new JFrame("Candy Crush");
        Game game = new Game();
        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(game).start();
    }

    public void update() {
        tabuleiro.update();
        boolean previous_out = (Game.previousx < 0 || Game.previousx >= Tabuleiro.WIDTH*Tabuleiro.GRID_SIZE) || (Game.previousy < 0 || Game.previousy >= Tabuleiro.HEIGHT*Tabuleiro.GRID_SIZE);
        boolean next_out = (Game.nextx < -1 || Game.nextx >= Tabuleiro.WIDTH*Tabuleiro.GRID_SIZE) || (Game.nexty < -1 || Game.nexty >= Tabuleiro.HEIGHT*Tabuleiro.GRID_SIZE);
        if(previous_out || next_out) {
            Game.nextx = -1;
            Game.nexty = -1;
            Game.selected = false;
        }
        if(Game.selected && Game.nextx != -1 && Game.nexty != -1) {
            int posx1 = Game.previousx/Tabuleiro.GRID_SIZE;
            int posy1 = Game.previousy/Tabuleiro.GRID_SIZE;

            int posx2 = Game.nextx/Tabuleiro.GRID_SIZE;
            int posy2 = Game.nexty/Tabuleiro.GRID_SIZE;

            if(((posx2 == posx1+1 || posx2 == posx1-1) && posy2 == posy1) || ( posx2 == posx1 && (posy2 == posy1-1 || posy2 == posy1+1))) {

                    int val1 = Tabuleiro.TABULEIRO[posx1][posy1];
                    int val2 = Tabuleiro.TABULEIRO[posx2][posy2];

                    Tabuleiro.TABULEIRO[posx1][posy1] = val2;
                    Tabuleiro.TABULEIRO[posx2][posy2] = val1;

                    Game.nexty = -1;
                    Game.nextx = -1;
                    Game.selected = false;
            } else {

            }
        }
    }
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(Color.cyan);
        g.fillRect(0,0,WIDTH,HEIGHT);

        tabuleiro.render(g);

        g = bs.getDrawGraphics();
        g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE, null);

        bs.show();
    }

    @Override
    public void run() {
        while(true) {
            update();
            render();
            try {
                Thread.sleep(FPS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(Game.selected == false) {
            Game.selected = true;
            Game.previousx = mouseEvent.getX()/SCALE - (WIDTH-Tabuleiro.WIDTH*Tabuleiro.GRID_SIZE)/2;
            Game.previousy = mouseEvent.getY()/SCALE - (HEIGHT-Tabuleiro.HEIGHT*Tabuleiro.GRID_SIZE)/2;
        } else {
            Game.nextx = mouseEvent.getX()/SCALE - (WIDTH-Tabuleiro.WIDTH*Tabuleiro.GRID_SIZE)/2;
            Game.nexty = mouseEvent.getY()/SCALE - (HEIGHT-Tabuleiro.HEIGHT*Tabuleiro.GRID_SIZE)/2;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
