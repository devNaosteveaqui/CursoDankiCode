package world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author samuel
 */
public class AStar {
    
    public static double lasttime = System.currentTimeMillis();
    private static Comparator<Node> nodeSorter = new Comparator<Node>(){
        @Override
        public int compare(Node n0, Node n1) {
            if(n1.fCost < n0.fCost) {
                return +1;
            }
            if(n1.fCost > n0.fCost) {
                return -1;
            }
            return 0;
        }
    };
    public static boolean clear() {
        if(System.currentTimeMillis() - lasttime >= 1000) {
            return true;
        }
        return false;
    }
    public static List<Node> findPath(World world, Vector2i start, Vector2i end) {
        lasttime = System.currentTimeMillis();
        List<Node> openlist = new ArrayList< Node>();
        List<Node> closelist = new ArrayList< Node>();
        
        Node current = new Node(start,null,0,getDistance(start,end));
        openlist.add(current);
        
        while(openlist.size() > 0) {
            Collections.sort(openlist,nodeSorter);
            current = openlist.get(0);
            if(current.tile.equals(end)) {
                List<Node> path = new ArrayList<Node>();
                while(current.parent != null) {
                    path.add(current);
                    current = current.parent;
                }
                openlist.clear();
                closelist.clear();
                return path;
            }
            openlist.remove(current);
            closelist.add(current);
            for(int i = 0; i < 9; i++) {
                if(i == 4) {
                    continue;
                }
                int x = current.tile.x;
                int y = current.tile.y;
                int xi = (i%3) - 1;
                int yi = (i/3) - 1;
                
                Tile tile = World.tiles[x+xi+((y + yi)*World.WIDTH)];
                if(tile == null) {
                    continue;
                }
                if(tile instanceof WallTile) {
                    continue;
                }
                if(i == 0) {
                    Tile teste = World.tiles[x+xi+1 + ((y + yi)*World.WIDTH)];
                    Tile teste2 = World.tiles[x+xi + ((y + yi+1)*World.WIDTH)];
                    if(teste instanceof WallTile || teste2 instanceof WallTile) {
                        continue;
                    }
                } else if(i == 2) {
                    Tile teste = World.tiles[x+xi-1 + ((y + yi)*World.WIDTH)];
                    Tile teste2 = World.tiles[x+xi + ((y + yi+1)*World.WIDTH)];
                    if(teste instanceof WallTile || teste2 instanceof WallTile) {
                        continue;
                    }
                } else if(i == 6) {
                    Tile teste = World.tiles[x+xi + ((y + yi - 1)*World.WIDTH)];
                    Tile teste2 = World.tiles[x+xi+1 + ((y + yi)*World.WIDTH)];
                    if(teste instanceof WallTile || teste2 instanceof WallTile) {
                        continue;
                    }
                } else if(i == 8) {
                    Tile teste = World.tiles[x+xi + ((y + yi - 1)*World.WIDTH)];
                    Tile teste2 = World.tiles[x+xi-1 + ((y + yi)*World.WIDTH)];
                    if(teste instanceof WallTile || teste2 instanceof WallTile) {
                        continue;
                    }
                }
                
                Vector2i a = new Vector2i(x+xi,y+yi);
                double gCost = current.gCost + getDistance(current.tile,a);
                double hCost = getDistance(a,end);
                
                Node node = new Node(a,current,gCost,hCost);
                if(vecInList(closelist,a) && gCost>= current.gCost) {
                    continue;
                }
                if(!vecInList(openlist,a)){
                    openlist.add(node);
                } else if(gCost < current.gCost) {
                    openlist.remove(current);
                    openlist.add(node);
                }
                
            }
        }
        closelist.clear();
        return null;
    }
    private static boolean vecInList(List<Node> list, Vector2i vector) {
        for(int i =0; i < list.size(); i++) {
            if(list.get(i).tile.equals(vector)) {
                return true;
            } 
        }
        return false;
    }
    private static double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.x - goal.x;
        double dy = tile.y - goal.y;
        
        return Math.sqrt(dx*dx + dy*dy);
    }
}
