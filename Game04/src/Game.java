import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener {
    public static int WIDTH = 640, HEIGHT = 480;
    public static int SCALE = 3;
    public static Player player;
    public World world;
    public List<Inimigo> inimigos = new ArrayList<Inimigo>();
    public Game() {
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        new Spritesheet();
        player = new Player(32,32);
        world = new World();
        inimigos.add(new Inimigo(32,32));
    }
    public void tick() {
        player.tick();
        for(int i =0; i <inimigos.size(); i++){
            inimigos.get(i).tick();
        }
    }
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(0,140,0));
        g.fillRect(0,0,WIDTH*SCALE,HEIGHT*SCALE);

        player.render(g);
        world.render(g);
        for(int i =0; i <inimigos.size(); i++){
            inimigos.get(i).render(g);
        }
        bs.show();
    }
    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.add(game);
        frame.setTitle("Mini Zelda");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(game).start();
    }
    @Override
    public void run() {
        while(true) {
            tick();
            render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode()==KeyEvent.VK_RIGHT) {
            player.right = true;
        } else if(keyEvent.getKeyCode()==KeyEvent.VK_LEFT) {
            player.left = true;
        }
        if(keyEvent.getKeyCode()==KeyEvent.VK_UP) {
            player.up = true;
        } else if(keyEvent.getKeyCode()==KeyEvent.VK_DOWN) {
            player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode()==KeyEvent.VK_RIGHT) {
            player.right = false;
        } else if(keyEvent.getKeyCode()==KeyEvent.VK_LEFT) {
            player.left = false;
        }
        if(keyEvent.getKeyCode()==KeyEvent.VK_UP) {
            player.up = false;
        } else if(keyEvent.getKeyCode()==KeyEvent.VK_DOWN) {
            player.down = false;
        }

        if(keyEvent.getKeyCode()==KeyEvent.VK_Z) {
            player.shoot = true;
        }
    }
}
