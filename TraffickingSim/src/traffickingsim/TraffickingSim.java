/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package traffickingsim;

import com.opengg.core.engine.GGApplication;
import com.opengg.core.engine.OpenGG;
import com.opengg.core.render.window.WindowInfo;

/**
 *
 * @author Javier
 */
public class TraffickingSim extends GGApplication{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WindowInfo w = new WindowInfo();
        w.name = "TraffickingSim";
        w.width = 1280;
        w.height = 960;
        OpenGG.initialize(new TraffickingSim(), w);
    }

    @Override
    public void setup() {
       
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {
        
    }
    
}
