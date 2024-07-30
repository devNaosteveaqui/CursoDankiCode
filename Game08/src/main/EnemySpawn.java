package main;

import entities.Enemy;
import entities.Entity;

public class EnemySpawn {
    public int targetTime = 60*2;
    public int curTime = 0;
    public void tick(){
        curTime++;
        if(curTime == targetTime) {
            targetTime = Entity.rand.nextInt(100);
            curTime=0;
            int yy = 0;
            int xx = Entity.rand.nextInt(Game.WIDTH-16);
            Enemy enemy = new Enemy(xx,yy,16,16,Entity.rand.nextInt(2)+1,Game.spritesheet.getSprite(32,16,16,16));
            Game.entities.add(enemy);
        }
    }
}
