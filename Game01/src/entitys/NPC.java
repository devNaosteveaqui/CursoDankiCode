package entitys;

import game01.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author samuel
 */
public class NPC extends Entity{
    
    public String[] frases;
    public boolean showmessage;
    public boolean show;
    
    public int curIndexMsg;
    public int fraseIndex;
    
    public int time,maxTime;
    
    public NPC(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        frases = new String[2];
        frases[0] = "Olá! Eu sou um Npc  o/";
        frases[1] = "...";
        showmessage = false;
        curIndexMsg = 0;
        fraseIndex = 0;
        time = 0;
        maxTime = 10;
    }
    
    @Override
    public void update() {
        depth = 2;
        int xplayer = Game.player.getX();
        int yplayer = Game.player.getY();
        
        int xnpc = (int) x;
        int ynpc = (int) y;
        
        if(Math.abs(xplayer - xnpc)<50 && Math.abs(yplayer - ynpc) < 50) {
            if(!show) {
                showmessage = true;
                show = true;
            }
        } else {
            //showmessage = false;
        }
        
        if(showmessage) {
            this.time++;
            if(this.time >= this.maxTime) {
                this.time = 0;
                if(curIndexMsg < frases[fraseIndex].length()) {
                    curIndexMsg++;
                } else {
                    if(fraseIndex < frases.length-1) {
                        fraseIndex++;
                        curIndexMsg = 0 ;
                    }
                }
            }
        }
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        if(showmessage) {
            g.setColor(Color.white);
            g.fillRect(9, 9, Game.WIDTH-18, Game.HEIGHT-18);
            g.setColor(Color.blue);
            g.fillRect(10, 10, Game.WIDTH-20, Game.HEIGHT-20);
            
            g.setColor(Color.black);
            g.setFont(new Font(Font.MONOSPACED,Font.BOLD,8));
            g.drawString(frases[fraseIndex].substring(0,curIndexMsg),(int) x, (int)y);
            
            g.drawString("Pressione Enter para fechar!", (int) x, (int) y + 10);
        }
    }
}
