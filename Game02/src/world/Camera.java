/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.world;

/**
 *
 * @author samuel
 */
public class Camera {
    public static int x = 0;
    public static int y = 0;
    
    public static int clamp(int xAtual, int xMin, int xMax) {
        if(xAtual < xMin) {
            xAtual = xMin;
        }
        if(xAtual > xMax) {
            xAtual = xMax;
        }
        return xAtual;
    }
}
