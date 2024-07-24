/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game01;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 *
 * @author samuel
 */
public class Sound {
    
    private AudioClip clip;
    
    public static final Sound hurtEffect = new Sound("hurt.wav");
    public static final Sound backgroundSound = new Sound("bgs.wav");
    
    private Sound(String name) {
        try {
            clip = Applet.newAudioClip(Sound.class.getResource(name));
        } catch (Throwable e) {
            System.err.print("Deu ruim");
        }
    }
    public void play() {
        try {
            new Thread() {
                @Override
                public void run() {
                    clip.play();
                }
            }.start();
        } catch (Throwable e) {}
    }
    public void loop() {
        try {
            new Thread() {
                @Override
                public void run() {
                    clip.loop();
                }
            }.start();
        } catch (Throwable e) {}
    }
}
