package main;

import world.*;

import java.awt.*;

public class Inventario {
    public String[] itens = {"grama","terra","neve","areia","ar",""};
    public int inventarioBoxSize = 50;
    public boolean isPressed = false, isPlaceItem = false;
    public int mx = 0, my= 0;
    public int selected = 0 ;
    public int initialPosition = (Game.WIDTH*Game.SCALE - itens.length*inventarioBoxSize)/2;


    public void tick() {
        if(isPressed) {
            isPressed = false;
            int x_min = initialPosition, x_max = initialPosition+(inventarioBoxSize*itens.length);
            int y_min = Game.HEIGHT*Game.SCALE-inventarioBoxSize+1, y_max = Game.HEIGHT*Game.SCALE+1;
            if(mx >= x_min && mx < x_max) {
                if(my >= y_min && my < y_max) {
                    selected = (int) ((mx-initialPosition)/inventarioBoxSize);
                }
            }
        }
        if(isPlaceItem) {
            System.out.println("Posso");
            isPlaceItem = false;

            mx = mx/3 + Camera.x;
            my = my/3 + Camera.y;

            int tilex = mx/16;
            int tiley = my/16;

            if(World.tiles[tilex+tiley*World.WIDTH].solid == false) {

                if(itens[selected] == "grama") {
                    World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_GRAMA);
                } else if(itens[selected] == "terra") {
                    World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_TERRA);
                } else if(itens[selected] == "ar") {
                    World.tiles[tilex+tiley*World.WIDTH] = new FloorTile(tilex*16, tiley*16,Tile.TILE_AR);
                } else if(itens[selected] == "neve") {
                    World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16,Tile.TILE_NEVE);
                } else if(itens[selected] == "areia") {
                    World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16,Tile.TILE_AREIA);
                }

                if(World.isFree(Game.player.getX(),Game.player.getY())==false) {
                    World.tiles[tilex+tiley*World.WIDTH] = new FloorTile(tilex*16, tiley*16,Tile.TILE_AR);
                }
            }
        }
    }
    public void render(Graphics g) {
        for(int i =0 ;i < itens.length; i++) {
            g.setColor(Color.gray);
            g.fillRect(initialPosition+i*inventarioBoxSize+1,Game.HEIGHT*Game.SCALE-inventarioBoxSize+1,inventarioBoxSize,inventarioBoxSize);
            g.setColor(Color.black);
            g.drawRect(initialPosition+i*inventarioBoxSize+1,Game.HEIGHT*Game.SCALE-inventarioBoxSize+1,inventarioBoxSize,inventarioBoxSize);
            if(itens[i] == "grama") {
                g.drawImage(Tile.TILE_GRAMA,initialPosition+i*inventarioBoxSize+10,Game.HEIGHT*Game.SCALE-inventarioBoxSize+10,28,28,null);
            } else if(itens[i] == "terra") {
                g.drawImage(Tile.TILE_TERRA,initialPosition+i*inventarioBoxSize+10,Game.HEIGHT*Game.SCALE-inventarioBoxSize+10,28,28,null);
            } else if(itens[i] == "ar") {
                g.drawImage(Tile.TILE_AR,initialPosition+i*inventarioBoxSize+10,Game.HEIGHT*Game.SCALE-inventarioBoxSize+10,28,28,null);
            }else if(itens[i] == "neve") {
                g.drawImage(Tile.TILE_NEVE,initialPosition+i*inventarioBoxSize+10,Game.HEIGHT*Game.SCALE-inventarioBoxSize+10,28,28,null);
            }else if(itens[i] == "areia") {
                g.drawImage(Tile.TILE_AREIA,initialPosition+i*inventarioBoxSize+10,Game.HEIGHT*Game.SCALE-inventarioBoxSize+10,28,28,null);
            }

            if(selected == i) {
                g.setColor(Color.red);
                g.drawRect(initialPosition+i*inventarioBoxSize,Game.HEIGHT*Game.SCALE-inventarioBoxSize,inventarioBoxSize,inventarioBoxSize);
            }
        }
    }
}
