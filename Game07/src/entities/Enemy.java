package entities;

import main.Game;
import world.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{
    public boolean right = true, left = false;
    public double vida = Entity.rand.nextInt(200-60)+60;
    public double maxvida = vida;
    public BufferedImage sprite1,sprite2;
    public int dir =1;
    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite1, BufferedImage sprite2) {
        super(x, y, width, height, speed, null);
        this.sprite1 = sprite1;
        this.sprite2 = sprite2;
    }

    public void tick() {
        if(World.isFree((int) x, (int) (y+1))){
            y+=1;
        }
        if(dir == 1) {
            if(World.isFree((int) (x+speed), (int) y)) {
                x+=speed;
            } else {
                int xnet = (int)((x+speed)/16);
                int ynet = (int)(y/16);
                Tile tile = World.tiles[xnet+1+ynet*World.WIDTH];
                if(tile instanceof WallTile && tile.solid == false) {
                    World.tiles[xnet+1+ynet*World.WIDTH] = new FloorTile((xnet+1)*16,ynet*16,Tile.TILE_AR);
                }
                dir = -1;
                left = true;
                right = false;
            }
        } else if(dir == -1) {
            if(World.isFree((int) (x-speed), (int) y)) {
                x-=speed;
            } else {
                int xnet = (int)((x-speed)/16);
                int ynet = (int)(y/16);
                Tile tile = World.tiles[xnet+ynet*World.WIDTH];
                if(tile instanceof WallTile && tile.solid == false) {
                    World.tiles[xnet+ynet*World.WIDTH] = new FloorTile((xnet)*16,ynet*16,Tile.TILE_AR);
                }
                dir =1;
                right = true;
                left = false;
            }
        }
        if(vida == 0) {
            Game.entities.remove(this);
            return;
        }
    }
    public void render(Graphics g) {
        if(right) {
            sprite = sprite1;
        } else if(left) {
            sprite = sprite2;
        }
        super.render(g);

        int curLife = (int) ((vida/maxvida)*20.0);
        g.setColor(Color.red);
        g.fillRect(this.getX()-10- Camera.x,this.getY()-10-Camera.y,20,5);
        g.setColor(Color.green);
        g.fillRect(this.getX()-10-Camera.x,this.getY()-10-Camera.y,curLife,5);
    }
}
