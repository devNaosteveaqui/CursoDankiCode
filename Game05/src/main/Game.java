package main;

import entities.Entity;
import entities.Player;
import graphics.Spritesheet;
import graphics.UI;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samuel
 */
public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    //Game Frame
    private static String game_name = "Super Mario";
    public static JFrame frame;
    private BufferedImage image;

    //Propriedades basicas do jogo
    public static final int WIDTH = 160;
    public static final int HEIGHT = 120;
    public static final int SCALE = 3;
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
        world = new World("level1.png");
        player = new Player((WIDTH - 16)/2,(HEIGHT - 16)/2,16,16,1,0.5,Entity.PLAYER_SPRITE_RIGHT[0]);
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
    public void tick() {
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
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

        world.render(g);
        //Renderizar do Jogo
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
                tick();
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
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = true;
        } else if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = true;
        }
        if(ke.getKeyCode() == KeyEvent.VK_SPACE) {
            player.jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = false;
        } else if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = false;
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
        g.fillRect(Game.WIDTH* Game.SCALE/2 - 1,0,1, Game.HEIGHT* Game.SCALE);
        g.fillRect(0, Game.HEIGHT* Game.SCALE/2 - 1, Game.WIDTH* Game.SCALE,1);
    }
    
}
