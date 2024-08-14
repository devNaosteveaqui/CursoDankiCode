package engine.core;

import engine.utilities.GameObject;

import java.awt.*;

public class GameObjectReferenceCaller{
    private GameObject obj;
    private String obj_class;
    private String obj_class_res;
    public GameObjectReferenceCaller(GameObject obj) {
        this.obj = obj;
        this.obj.reference = this;
        this.obj_class = obj.getClass().getSimpleName();
        this.obj_class_res = obj.getClass().getName();

    }
    public void tick_process() {
        obj.tick_process();
    }
    public void render_process(Graphics g) {
        obj.render_process(g);
    }

}
