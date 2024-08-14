package engine.utilities;

import engine.core.GameEngine;
import engine.core.GameObjectReferenceCaller;
import engine.graphics.GameGraphicsObject;
import engine.graphics.GameObjectSprite;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GameObject extends GameGraphicsObject {
    public GameObjectReferenceCaller reference;
    private List<GameObject> childs;
    public GameObject() {
        childs = new ArrayList<GameObject>();
    }
    public void event_process() {}
    public void tick_process() {}
    @Override
    public void render_process(Graphics g) {}
    public void addChild(GameObject obj) {
        childs.add(obj);
        GameEngine.addGameObject(obj,0);
    }
    public GameObject getChild(int i) {
        return childs.get(i);
    }
    public int getChildCount() {
        return childs.size();
    }
    public void removeChild(int i) {
        GameEngine.removeGameObject(childs.get(i),0);
        childs.remove(i);
    }
    public void removeChild(GameObject obj) {
        childs.remove(obj);
        GameEngine.removeGameObject(obj,0);
    }
}
