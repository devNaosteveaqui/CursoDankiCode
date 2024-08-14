package engine.core;

import engine.graphics.Vector2D;
import engine.utilities.GameObject;
import game.MyGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameEngine extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{
    //Game Frame
    private static String game_name = "Game Engine";
    public static JFrame frame;
    private BufferedImage image;

    //Propriedades basicas do jogo
    public static Vector2D WindowSize;
    public static Vector2D WindowScale;
    //Sistema de LoopGame
    private Thread thread;
    private boolean isRunning;
    private final double amountOfTicks = 60.0;
    private final double ns = 1000000000/amountOfTicks;
    //ELEMENTOS DO GAME ENGINE
    public static List<GameObjectReferenceCaller> gorc;
    //TESTE
    public static int pc = 0;
    public static int pcmax = 100000;
    public static GameObject root_game;
    public static Random rand;
    GameEngine() {
        WindowSize = new Vector2D(160,160);
        WindowScale = new Vector2D(3,3);
        rand = new Random();
        addKeyListener(this);
        addMouseListener(this);
        setPreferredSize(new Dimension((int) (WindowSize.x*WindowScale.x), (int) (WindowSize.x*WindowScale.x)));
        initFrame();
        image = new BufferedImage((int) WindowSize.x, (int) WindowSize.y,BufferedImage.TYPE_INT_RGB);
        gorc = new ArrayList<GameObjectReferenceCaller>();

        addGameObject(new MyGame(),0);
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
    //REVISAR START E STOP
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
    public void tick_process() {
        for (GameObjectReferenceCaller n : gorc) n.tick_process();
    }
    public void render_process() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        //Limpa a Tela
        g.setColor(new Color(80,80,80));
        g.fillRect(0, 0, (int) WindowSize.x, (int) WindowSize.y);

        for (GameObjectReferenceCaller n : gorc) n.render_process(g);

        //Renderiza tudo em um frame
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage((Image) image, 0, 0, (int) (WindowSize.x*WindowScale.x), (int) (WindowSize.x*WindowScale.x), null);

        bs.show();
    }

    public static void main(String[] args) {
        GameEngine game = new GameEngine();
        game.start();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        for (GameObjectReferenceCaller n : gorc) n.tick_process();
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
    //REVISAR CODIGO
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
                tick_process();
                render_process();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000) {
                //System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }

        stop();
    }
    public static void addGameObject(GameObject obj,int layer) {
        GameObjectReferenceCaller objRC = new GameObjectReferenceCaller(obj);
        GameEngine.gorc.add(objRC);
    }
    public static void removeGameObject(GameObject obj, int layer) {
        GameEngine.gorc.remove(obj.reference);
    }

}
