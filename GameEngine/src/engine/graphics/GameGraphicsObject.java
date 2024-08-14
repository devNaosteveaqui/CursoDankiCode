package engine.graphics;

import java.awt.*;

public abstract class GameGraphicsObject {
    public Vector2D position;
    public Vector2D size;
    public Vector2D scale;
    public int layer;

    public abstract void render_process(Graphics g);
}
