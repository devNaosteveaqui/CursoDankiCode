package main;

import entities.Enemy;
import entities.Entity;
import world.World;

public class EnemySpawn {
    public int interval = 60*2;
    public int curTime = 0;
    public void tick() {
        curTime++;
        if(curTime == interval) {
            curTime = 0;
            int xInitial = Entity.rand.nextInt(World.WIDTH*8 - 32)+16;
            Enemy enemy = new Enemy(xInitial,16,16,16,1, Entity.ENEMY_LEFT,Entity.ENEMY_RIGHT);
            Game.entities.add(enemy);
        }
    }
}
