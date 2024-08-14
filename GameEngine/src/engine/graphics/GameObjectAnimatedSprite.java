package engine.graphics;

import engine.utilities.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameObjectAnimatedSprite extends GameObject {
    public Map<String,List<GameObjectSprite>> animation_sprites;
    //public List<GameObjectSprite> sprites;
    public String animation_name;
    public String animation_now = "default";
    private int fps;
    public int sprite_index;
    public GameObjectAnimatedSprite(String name) {
        super();
        animation_name = name;
        animation_sprites = new HashMap<>();
    }
    public GameObjectAnimatedSprite(String name, int nSprite) {
        super();
        animation_name = name;
        animation_sprites = new HashMap<>();
    }
    public void addSprite(String animation, GameObjectSprite sprite) {
        if(animation_sprites.containsKey(animation)) {
            animation_sprites.get(animation).add(sprite);
        } else {
            List<GameObjectSprite> sprites = new ArrayList<GameObjectSprite>();
            sprites.add(sprite);
            animation_sprites.put(animation,sprites);
        }
    }
    public void removeSprite(String animation, GameObjectSprite sprite) {
        if(animation_sprites.containsKey(animation)) animation_sprites.get(animation).remove(sprite);
    }
    public void removeSprite(String animation, int sprite) {
        if(animation_sprites.containsKey(animation)) animation_sprites.get(animation).remove(sprite);
    }
    @Override
    public void tick_process() {
        super.tick_process();
        fps++;
        if(fps%12 == 0) {
            sprite_index++;
            if (sprite_index == animation_sprites.get(animation_now).size()) {
                sprite_index = 0;
            }
        }
        fps = fps%60;
    }

    @Override
    public void render_process(Graphics g) {
        super.render_process(g);
        animation_sprites.get(animation_now).get(sprite_index).render_process(g);
    }
}
