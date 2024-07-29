package engine.core;

import javax.security.auth.callback.Callback;
import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class GameObjectReferenceCaller{
    private GameObject obj;
    private String obj_class;
    public GameObjectReferenceCaller(GameObject obj, String obj_class) {
        this.obj = obj;
        this.obj_class = obj_class;
    }
    public void tick_process() {
        obj.tick_process();
    }
    public void render_process(Graphics g) {
        obj.render_process(g);
    }

}
