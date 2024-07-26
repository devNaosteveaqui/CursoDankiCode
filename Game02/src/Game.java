package pacman;

import static pacman.Game.entities;
import static pacman.Game.player;
import static pacman.Game.spritesheet;
import static pacman.Game.world;
import pacman.entities.Entity;
import pacman.entities.Player;
import pacman.graphics.Spritesheet;
import pacman.graphics.UI;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import pacman.world.World;

/**
 *
 * @author samuel
 */
public class Game extends Canvas implements Runnable, KeyListener, MouseListener,MouseMotionListener{

    //Game Frame
    private static String game_name = "Pac Man";
    public static JFrame frame;
    private BufferedImage image;
    
//Propriedades basicas do jogo
    public static final int WIDTH = 240;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;    
//Entidades
    public static Spritesheet spritesheet;
    public static List<Entity> entities;
    public static Player player;
    public static World world;
    
    //Sistema de LoopGame
    private Thread thread;
    private boolean isRunning;
    private final double amountOfTicks = 60.0;
    private final double ns = 1000000000/amountOfTicks;
    
    //Game Interface
    public UI ui;
    
    public static int frutas_atual = 0;
    public static int frutas_contagem = 0;
    
    public static Random rand;
    public Game() {
        rand = new Random();
        addKeyListener(this);
        addMouseListener(this);
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        initFrame();
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        spritesheet = new Spritesheet("spritesheet.png");
        entities = new ArrayList<Entity>();
        
        player = new Player(0,0,16,16,1,spritesheet.getSprite(32, 16, 16, 16));
        world = new World("level1.png");
        ui = new UI();
        
        entities.add(Game.player);
    }
    private void initFrame() {
        frame = new JFrame(game_name);
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }
    
    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateGame() {
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.update();
        }
    }
    //Metodo de renderização do jogo
    public void renderGame() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0,0,0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //Renderizar do Jogo
        world.render(g);
        Collections.sort(entities,Entity.nodeSorter);
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }
        //Render all in frame
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        ui.render(g);
        bs.show();
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >= 1) {
                updateGame();
                renderGame();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }
        
        stop();
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT ||
                ke.getKeyCode() == KeyEvent.VK_D) {
            //Direita
            player.right = true;
        } else if(ke.getKeyCode() == KeyEvent.VK_LEFT ||
                ke.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }
        if(ke.getKeyCode() == KeyEvent.VK_UP ||
                ke.getKeyCode() == KeyEvent.VK_W) {
            //Cima
            player.up = true;
        } else if(ke.getKeyCode() == KeyEvent.VK_DOWN ||
                ke.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT ||
                ke.getKeyCode() == KeyEvent.VK_D) {
            //Direita
            player.right = false;
        } else if(ke.getKeyCode() == KeyEvent.VK_LEFT ||
                ke.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }
        if(ke.getKeyCode() == KeyEvent.VK_UP ||
                ke.getKeyCode() == KeyEvent.VK_W) {
            //Cima
            player.up = false;
        } else if(ke.getKeyCode() == KeyEvent.VK_DOWN ||
                ke.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
    
    public static void drawLineGuide(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(Game.WIDTH*Game.SCALE/2 - 1,0,1,Game.HEIGHT*Game.SCALE);
        g.fillRect(0,Game.HEIGHT*Game.SCALE/2 - 1,Game.WIDTH*Game.SCALE,1);
    }
}
