import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable, MouseListener {
    public static int WIDTH = 480;
    public static int HEIGHT = 480;
    public static List<Smoke> smokes;
    public static List<Crab> crabs;
    public Spawner spawner;
    public static Spritesheet spritesheet;
    public static Rectangle maskHole;
    public static int mx,my;
    public static boolean isPressed = false;
    public static int score = 0;
    public Game() {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.addMouseListener(this);
        spritesheet = new Spritesheet("spritesheet.png");
        crabs = new ArrayList<>();
        smokes = new ArrayList<>();
        spawner = new Spawner();
        maskHole = new Rectangle(WIDTH/2-20,HEIGHT/2-20,40,40);
    }
    public void update() {
        spawner.update();
        for(int i = 0; i<crabs.size();i++) {
            crabs.get(i).update();
        }
        for(int i = 0; i<smokes.size(); i++) {
            smokes.get(i).update();
        }
    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        //BACKGROUND
        g.setColor(new Color(255,229,102));
        g.fillRect(0,0,WIDTH,HEIGHT);

        //HOLE
        g.setColor(Color.BLACK);
        g.fillOval(WIDTH/2 -20,HEIGHT/2 - 20,40,40);

        //Crabs
        for(int i = 0; i<crabs.size();i++) {
            crabs.get(i).render(g);
        }
        //Smoke
        for(int i = 0; i<smokes.size(); i++) {
            smokes.get(i).render(g);
        }

        //Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("arial",Font.BOLD,17));
        g.drawString("Score: "+score,10,15);

        g.dispose();
        bs.show();

    }
    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.setTitle("Catch the Crab");
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        new Thread(game).start();
    }

    @Override
    public void run() {
        double fps = 60.0;
        while(true) {
            update();
            render();
            try {
                Thread.sleep((int) (1000/fps));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        isPressed = true;
        mx = mouseEvent.getX();
        my = mouseEvent.getY();
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
}
