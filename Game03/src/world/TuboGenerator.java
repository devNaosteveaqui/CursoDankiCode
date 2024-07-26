package world;

import entities.Entity;
import entities.Tubo;
import main.Game03_FlappyBird;

public class TuboGenerator {
    public int time =0 ;
    public int time_target = 60;

    public void tick() {
        time++;
        if(time == time_target) {
            int altura1 = Entity.rand.nextInt(70-30)+30;
            Tubo tubo1 = new Tubo(Game03_FlappyBird.WIDTH,0,20,altura1,1,Game03_FlappyBird.spritesheet.getSprite(32,32,16,16));


            int altura2 = Entity.rand.nextInt(70-30)+30;
            Tubo tubo2 = new Tubo(Game03_FlappyBird.WIDTH,Game03_FlappyBird.HEIGHT-altura2,20,altura2,1,null);

            Game03_FlappyBird.entities.add(tubo1);
            Game03_FlappyBird.entities.add(tubo2);

            time = 0;
        }
    }

}
