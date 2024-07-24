package game01;


import entitys.BulletShoot;
import entitys.Enemy;
import entitys.Entity;
import entitys.NPC;
import entitys.Player;
import graficos.Spritesheet;
import graficos.UI;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import world.Camera;
import world.World;

/**
 *
 * @author samuel
 */
public class Game extends Canvas implements Runnable, KeyListener, MouseListener,MouseMotionListener{

    private static final long serialVersionUID = 1L;
    private static String game_name = "Game #1";

    private Thread thread;
    private boolean isRunning;

    public static JFrame frame;
    private BufferedImage image;
    public static Spritesheet spritesheet;
    
    public static List<Entity> entities;
    public static List<Enemy> enemys;
    public static List<BulletShoot> bullets;
    public static Player player;
    public NPC npc;
    public static World world;
    private int CUR_LEVEL = 1,MAX_LEVEL = 2;
    
    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3;
    
    public static final String MENU = "MENU";
    public static final String NORMAL = "NORMAL";
    public static final String GAME_OVER = "GAME_OVER";
    
    private final double amountOfTicks = 60.0;
    private final double ns = 1000000000/amountOfTicks;
    
    public static Random rand;
    
    public UI ui;
    /*public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("font.ttf");
    public Font newFont;*/
    public Menu menu;
    
    public static String gameState = Game.MENU;
    private boolean showMessageGameOver = false;
    private int frameGameOver = 0;
    private boolean restartGame = false;
    
    public boolean saveGame = false;
    
    //public int mx,my;
    //Systema de cutscene
    public static int entrada = 1;
    public static int comecar = 2;
    public static int jogando = 3;
    public static int estado_cena = 1;
    public int timeCena = 0, maxTimeCena = 60;
    
    public int[] pixels;
    public BufferedImage lightmap;
    public int[] lightmappixels;
    
    public static BufferedImage minimap;
    public static int[] minimappixels;
    
    public Game() {
        /*try {
            newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(30f);
        } catch (FontFormatException ex) {} catch (IOException ex) {}
        */
        //Sound.backgroundSound.loop();
        //BetterSound.music.loop();
        rand = new Random();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        //setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));//FullScreen
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        initFrame();
        //Iniciando objetos
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        try {
            lightmap = ImageIO.read(getClass().getResource("lightmap.png"));
        } catch (IOException ex) {}
        lightmappixels = new int[lightmap.getWidth()*lightmap.getHeight()];
        lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightmappixels, 0, lightmap.getWidth());
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        
        entities = new ArrayList<Entity>();
        enemys = new ArrayList<Enemy>();
        bullets = new ArrayList<BulletShoot>();
        
        spritesheet = new Spritesheet("spritesheet.png");
        player = new Player(0,0,16,16,spritesheet.getSprite(32, 0, 16, 16));
        entities.add(Game.player);
        
        npc = new NPC(32,32,16,16,spritesheet.getSprite(32, 64, 16, 16));
        entities.add(npc);
        
        world = new World("level1.png");
        
        minimap = new BufferedImage(World.WIDTH,World.HEIGHT,BufferedImage.TYPE_INT_RGB);
        minimappixels = ((DataBufferInt)minimap.getRaster().getDataBuffer()).getData();
        ui = new UI();
        menu = new Menu();
    }
    private void initFrame() {
        frame = new JFrame(game_name);
        frame.add(this);
        //frame.setUndecorated(true);//first step to fullscreen
        frame.setResizable(false);
        frame.pack();
        
        /*Image window_icon = null;
        try {
            window_icon = ImageIO.read(getClass().getResource("window_icon.png"));
        } catch(IOException ex) {}
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursor_icon = toolkit.getImage(getClass().getResource("cursor_icon.png"));
        Cursor c = toolkit.createCustomCursor(cursor_icon, new Point(0,0), "img");
        
        frame.setCursor(c);
        frame.setIconImage(window_icon);
        frame.setAlwaysOnTop(true);
        */
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
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
    
    public void updateGame() {
        if(gameState.equals(Game.NORMAL)) {
            if(this.saveGame) {
                this.saveGame = false;
                String[] opt1 = {"level","vida"};
                int[] opt2 = {this.CUR_LEVEL,(int) player.vida};
                Menu.saveGame(opt1, opt2, 10);
                System.out.println("Sava Game Susseful");
            }
            this.restartGame = false;
            
            Collections.sort(entities,Entity.nodeSorter);
            if(estado_cena == Game.jogando) {
                for(int i = 0; i < entities.size(); i++) {
                    Entity e = entities.get(i);
                    e.update();
                }
                for(int i = 0; i < bullets.size(); i++) {
                    bullets.get(i).update();
                }
            } else {
                if(estado_cena == Game.entrada) {
                    System.out.println("Entrando na CutScene.");
                    if(player.getY() > Game.HEIGHT*Game.SCALE-250) {
                        player.y--;
                        System.out.println("Movendo Player.");
                    } else {
                        estado_cena = Game.comecar;
                        System.out.println("Começando jogo.");
                    }
                } else if(estado_cena == Game.comecar) {
                    timeCena++;
                    if(timeCena == maxTimeCena) {
                        estado_cena = Game.jogando;
                        System.out.println("Iniciando Jogo.");
                    }
                }
            }
            /*if(enemys.size() == 0) {
                CUR_LEVEL++;
                if(CUR_LEVEL > MAX_LEVEL) {
                    CUR_LEVEL = 1;
                }
                String newWorld = "level" + CUR_LEVEL + ".png";
                World.restartGame(newWorld);
            }*/
        }else if(gameState.equals(Game.GAME_OVER)){
            this.frameGameOver++;
            if(this.frameGameOver  == 15) {
                this.frameGameOver = 0;
                if(this.showMessageGameOver) {
                    this.showMessageGameOver = false;
                } else {
                    this.showMessageGameOver = true;
                }
            }
            if(restartGame) {
                this.restartGame = false;
                this.gameState = Game.NORMAL;
                this.CUR_LEVEL = 1;
                String newWorld = "level" + CUR_LEVEL + ".png";
                World.restartGame(newWorld);
            }
        } else if(gameState.equals(Game.MENU)) {
            player.updateCamera();
            menu.update();
        }
    }
    /*public void drawRectangle(int xoffset, int yoffset) {
        for(int i = 0; i < 32; i++) {
            for(int j = 0; j < 32; j++) {
                int xOff = i + xoffset;
                int yOff = j + yoffset;
                if((xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT)) 
                    continue;
                pixels[xOff + (yOff*WIDTH)] = 0xff0000;
            }
        }
    }*/
    public void applyLight() {
        for(int i = 0; i < Game.WIDTH; i++) {
            for(int j = 0; j < Game.HEIGHT; j++) {
                if(lightmappixels[i + (j*Game.WIDTH)] == 0xffffffff) {
                    int pixel = Pixel.getLightBlend(pixels[i+(j*Game.WIDTH)], 0x808080, 0);
                    pixels[i + (j*Game.WIDTH)] = pixel;
                }
            }
        }
    }
    public void renderGame() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        //Render Background
        g.setColor(new Color(0,0,0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        world.render(g);
        //Render Game
        //Graphics2D g2 = (Graphics2D) g;
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }
        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
        applyLight();
        ui.render(g);
        //Render all in frame
        g.dispose();
        g = bs.getDrawGraphics();
        //g.drawImage(image, 0, 0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height, null);
        //TUDO que está abaixo deve ser ajustado para serem inseridos dentro da imagem
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        g.setFont(new Font(Font.MONOSPACED,Font.BOLD,12));
        //g.setFont(newFont);
        g.drawString("Munição: " + player.ammo, (WIDTH - 30)*SCALE, 27);
        
        if(gameState.equals(Game.GAME_OVER)) {
            //Game.drawLineGuide(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0,0,0,100));
            g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
            
            g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,28));
            g2.setColor(Color.white);
            g2.drawString("Game Over", WIDTH*SCALE/2 - 75, HEIGHT*SCALE/2 - 6);
            g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
            if(showMessageGameOver)
                g2.drawString("Pressione ENTER para reiniciar.", WIDTH*SCALE/2 - 120, HEIGHT*SCALE/2 + 21);
        } else if(gameState.equals(Game.MENU)) {
            menu.render(g);
        }
        /*Graphics2D g2 = (Graphics2D) g;
        double angleMouse = Math.atan2(my-200-25, mx-200-25);
        g2.rotate(angleMouse,225,225);
        g.setColor(Color.red);
        g.drawRect(200, 200, 50, 50);
        */
        World.renderMiniMap();
        g.drawImage(minimap, 600, 360,World.WIDTH*5,World.HEIGHT*5, null);
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
    public void keyTyped(KeyEvent ke) {
        
    }

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
            if(gameState.equals(Game.MENU)) {
                menu.up = true;
            }
        } else if(ke.getKeyCode() == KeyEvent.VK_DOWN ||
                ke.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
            if(gameState.equals(Game.MENU)) {
                menu.down = true;
            }
        }
        if(ke.getKeyCode() == KeyEvent.VK_X) {
            player.shoot = true;
        }
        if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
            this.restartGame = true;
            if(npc.showmessage) {
                npc.showmessage = false;
            }
            if(gameState.equals(Game.MENU)) {
                menu.enter = true;
            }
        }
        if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = Game.MENU;
            menu.pause = true;
        }
        if(ke.getKeyCode() == KeyEvent.VK_Z) {
            player.jump = true;
        }
        if(ke.getKeyCode() == KeyEvent.VK_SPACE) {
            if(Game.gameState.equals(Game.NORMAL)){
                this.saveGame = true;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {
        player.mouseshoot = true;
        player.mx = (me.getX()/SCALE);
        player.my = (me.getY()/SCALE);
    }

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
    public static void drawLineGuide(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(Game.WIDTH*Game.SCALE/2 - 1,0,1,Game.HEIGHT*Game.SCALE);
        g.fillRect(0,Game.HEIGHT*Game.SCALE/2 - 1,Game.WIDTH*Game.SCALE,1);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //this.mx = e.getX();
        //this.my = e.getY();
    }
}
