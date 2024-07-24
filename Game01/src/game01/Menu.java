package game01;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import world.World;

/**
 *
 * @author samuel
 */
public class Menu {
    
    public String[] options = {"Novo Jogo","Carregar Jogo","Sair"};
    public int currentOption = 0;
    public int maxOptions = options.length - 1;
    public boolean down, up,enter;
    public static boolean pause;
    public static boolean saveExists;
    public static boolean saveGame;
    
    public void update() {
        File file = new File("save.txt");
        if(file.exists()) {
            saveExists = true;
        } else {
            saveExists = false;
        }
        if(up) {
            up = false;
            currentOption--;
            if(currentOption < 0) {
                currentOption = maxOptions;
            }
        }
        if(down) {
            down = false;
            currentOption++;
            if(currentOption > maxOptions) {
                currentOption = 0;
            }
        }
        if(enter) {
            enter = false;
            if(options[currentOption] == "Novo Jogo" || options[currentOption] == "Continuar") {
                Game.gameState = Game.NORMAL;
                pause = false;
                file = new File("save.txt");
                file.delete();
            } else if(options[currentOption] == "Carregar Jogo") {
                file = new File("save.txt");
                if(file.exists()) {
                    String saver = loadGame(10);
                    applySave(saver);
                }
            }else if(options[currentOption] == "Sair") {
                System.exit(1);
            }
        }
    }
    public static void applySave(String str) {
        String[] spl = str.split("/");
        for(int i =0; i < spl.length; i++) {
            String[] spl2 = spl[i].split(":");
            switch(spl2[0]) {
                case "level" :
                    World.restartGame("level" + spl2[1] + ".png");
                    Game.gameState = Game.NORMAL;
                    pause = false;
                    break;
                case "vida" :
                    Game.player.vida = Integer.parseInt(spl2[1]);
                    break;
            }
        }
    }
    public static String loadGame(int encode) {
        String line = "";
        File file = new File("save.txt");
        if(file.exists()) {
            try {
                String singleLine = null;
                BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
                try {
                    while((singleLine = reader.readLine()) != null) {
                        String[] trans = singleLine.split(":");
                        char[] val = trans[1].toCharArray();
                        trans[1] = "";
                        for(int i =0; i < val.length; i++) {
                            val[i] -= encode;
                            trans[1]+=val[i];
                        }
                        line+=trans[0];
                        line+=":";
                        line+=trans[1];
                        line+="/";
                    }
                } catch(IOException ex) {}
            } catch (FileNotFoundException ex) {}
        }
        return line;
    }
    public static void saveGame(String[] val,int[] val2, int encode) {
        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new FileWriter("save.txt"));
        } catch(IOException ex) {}
        for(int i =0; i<val.length;i++) {
            String current = val[i];
            current += ":";
            //Encodificação
            char[] value = Integer.toString(val2[i]).toCharArray();
            for(int j = 0; j < value.length; j++) {
                value[j] += encode;
                current += value[j];
            }
            try {
                write.write(current);
                if(i < val.length - 1) {
                    write.newLine();
                }
            }catch(IOException ex){}
        }
        try {
            write.flush();
            write.close();
        } catch(IOException ex) {}
    }
    
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0,0,0,100));
        g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
        //Game.drawLineGuide(g);
        g.setColor(Color.white);
        
        g.setFont(new Font(Font.MONOSPACED,Font.BOLD,24));
        g.drawString("Zelda Clone", Game.WIDTH*Game.SCALE/2 - 80, 40);
        
        g.setFont(new Font(Font.MONOSPACED,Font.BOLD,12));
        if(!pause) {
            g.drawString(options[0], Game.WIDTH*Game.SCALE/2 - 34, Game.HEIGHT*Game.SCALE/2 - 17);
        } else {
            g.drawString("Continuar", Game.WIDTH*Game.SCALE/2 - 34, Game.HEIGHT*Game.SCALE/2 - 17);
        }
        g.drawString(options[1], Game.WIDTH*Game.SCALE/2 - 49, Game.HEIGHT*Game.SCALE/2  + 3);
        g.drawString(options[2], Game.WIDTH*Game.SCALE/2 - 16, Game.HEIGHT*Game.SCALE/2  + 23);
        
        if(options[currentOption].equals("Novo Jogo")) {
            g.drawString(">", Game.WIDTH*Game.SCALE/2 - 34 - 16, Game.HEIGHT*Game.SCALE/2 - 17);
        } else if(options[currentOption].equals("Carregar Jogo")) {
            g.drawString(">", Game.WIDTH*Game.SCALE/2 - 49 - 16, Game.HEIGHT*Game.SCALE/2 + 3);
        } else if(options[currentOption].equals("Sair")) {
            g.drawString(">", Game.WIDTH*Game.SCALE/2 - 16 - 16, Game.HEIGHT*Game.SCALE/2 + 23);
        }
    }
}