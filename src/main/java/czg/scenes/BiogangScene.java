/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.util.Images;

import static czg.MainWindow.HEIGHT;

/**
 *
 * @author guest-nie2d3
 */
public class BiogangScene extends BaseScene{
    public BiogangScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/Biogang.png")));
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    erstesOGScene erstes = new erstesOGScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    erstes.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, erstes);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    TreppeLinks2Scene tl2 = new TreppeLinks2Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tl2.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tl2);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
        
    }

}
