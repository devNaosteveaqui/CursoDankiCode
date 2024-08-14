package game;

import engine.core.GameEngine;
import engine.graphics.GameGraphics;
import engine.graphics.GameObjectAnimatedSprite;
import engine.graphics.Vector2D;
import engine.utilities.GameObject;
import engine.graphics.GameObjectSprite;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class MyGame extends GameObject {
    public MyGame() {
        super();
        GameGraphics.loadResource("/spritesheet_platform_01.png");
        this.position = new Vector2D(GameEngine.WindowSize.x/2 - 8,GameEngine.WindowSize.y/2 - 8);
        this.size = new Vector2D(16,16);
        GameObjectAnimatedSprite animated_sprite = new GameObjectAnimatedSprite("idle",5);
        for(int i = 0 ; i<5; i++) {
            GameObjectSprite sprite = new GameObjectSprite();
            sprite.image = GameGraphics.getSprite((int) (i*this.size.x),0, (int) this.size.x, (int) this.size.y);
            sprite.position = this.position;
            sprite.size = this.size;
            animated_sprite.addSprite(animated_sprite.animation_now,sprite);
        }
        for(int i = 0 ; i<5; i++) {
            GameObjectSprite sprite = new GameObjectSprite();
            sprite.image = GameGraphics.getSprite((int) (i*this.size.x),32, (int) this.size.x, (int) this.size.y);
            sprite.position = this.position;
            sprite.size = this.size;
            animated_sprite.addSprite("run",sprite);
        }

        this.addChild(animated_sprite);
    }

    @Override
    public void tick_process() {
        super.tick_process();

    }

    @Override
    public void render_process(Graphics g) {
        super.render_process(g);
    }
}

