/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellolwjgl;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asrianCron
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game.start();
//        long lastFrame = System.currentTimeMillis();
//        long currentFrame;
//        while (true) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            currentFrame = System.currentTimeMillis();
//            if (currentFrame > (lastFrame + 1000l)) {
//                lastFrame = System.currentTimeMillis();
//                System.out.println(true);
//            }
//        }
    }

}
