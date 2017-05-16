/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package traffickingsim;

import com.opengg.core.engine.GGApplication;
import com.opengg.core.engine.OpenGG;
import com.opengg.core.engine.ProjectionData;
import com.opengg.core.engine.RenderEngine;
import com.opengg.core.engine.Resource;
import com.opengg.core.engine.WorldEngine;
import com.opengg.core.gui.GUI;
import com.opengg.core.gui.GUIText;
import com.opengg.core.math.Quaternionf;
import com.opengg.core.math.Vector2f;
import com.opengg.core.math.Vector3f;
import com.opengg.core.render.Text;
import com.opengg.core.render.objects.ObjectCreator;
import com.opengg.core.render.shader.ShaderController;
import com.opengg.core.render.texture.Cubemap;
import com.opengg.core.render.texture.text.GGFont;
import com.opengg.core.render.window.WindowInfo;
import com.opengg.core.world.components.CameraComponent;

/**
 *
 * @author Javier
 */
public class TraffickingSim extends GGApplication{
    AmericaComponent freedom;
    
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
       freedom = new AmericaComponent();
       freedom.setPositionOffset(new Vector3f(0,-10,0));
       freedom.setRotationOffset(new Quaternionf(new Vector3f(-89f, 40f, 40f)));
       CameraComponent freedomview = new CameraComponent();
       freedomview.setRotationOffset(new Quaternionf(new Vector3f(-89f,0f,0f)));
       freedomview.setPositionOffset(new Vector3f(0,100f,0));
       freedomview.use();
       
       WorldEngine.getCurrent().attach(freedom);
       WorldEngine.getCurrent().attach(freedomview);
       WorldEngine.getCurrent().useRenderables();
       
       RenderEngine.setCulling(false);
       RenderEngine.setSkybox(ObjectCreator.createCube(1500f), Cubemap.get("C:\\res\\skybox\\majestic"));
       
       GGFont font = new GGFont(Resource.getTexturePath("graytest.png"), Resource.getFontPath("test"));
       Text text = new Text("", new Vector2f(), 1f, 0.4f, false);
       
       GUI.addItem("totviolence", new GUIText(text.copyFormat("Sonic"), font, new Vector2f(0,0)));
       GUI.addItem("totarms", new GUIText(text.copyFormat("Knuckles"), font, new Vector2f(1,0)));
       GUI.addItem("totowner", new GUIText(text.copyFormat("Tails"), font, new Vector2f(1,0.2f)));
       GUI.addItem("totsurplus", new GUIText(text.copyFormat("Amy"), font, new Vector2f(0,0.2f)));
       
    }

    @Override
    public void render() {
        ShaderController.setPerspective(90, OpenGG.window.getRatio(), 0.2f, 3000f);
        RenderEngine.draw();
    }

    @Override
    public void update() {

    }
    
}
