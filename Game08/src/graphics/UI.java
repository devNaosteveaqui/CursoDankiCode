package graphics;

import entities.Player;
import main.Game;
import world.World;

import java.awt.*;

/**
 *
 * @author samuel
 */
public class UI {
    public static int seconds = 0;
    public static int minute = 0;
    public static int frames = 0;
    public void tick() {
        frames++;
        if(frames==60) {
            frames = 0;
            seconds++;
            if(seconds==60){
                seconds = 0;
                minute++;
                if(UI.minute%2 == 0) {
                    World.CICLO++;
                    if(World.CICLO > World.noite) {
                        World.CICLO = World.dia;
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        String formatTime ="";
        if(minute<10) {
            formatTime+="0"+minute+":";
        } else {
            formatTime+=minute+":";
        }
        if(seconds<10) {
            formatTime+="0"+seconds;
        } else {
            formatTime+=seconds;
        }
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.BOLD,17));
        g.drawString("Score: " + Game.score,10,20);

        g.setColor(Color.red);
        g.fillRect(Game.WIDTH*Game.SCALE - 155,6, 150,25);
        g.setColor(Color.green);
        g.fillRect(Game.WIDTH*Game.SCALE - 155,6, (int) ((Game.life/100)*150),25);


    }
}
