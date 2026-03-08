/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.util.Images;

/**
 *
 * @author guest-ku1dtt
 */
public class FoyerScene extends BaseScene{
    public FoyerScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/Foyer.png")));
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    GangHausmeisterScene hausm = new GangHausmeisterScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    hausm.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, hausm);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    PhysikgangScene füüüü = new PhysikgangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    füüüü.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, füüüü);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);

        ButtonObject oben = new ButtonObject(
                Images.get("/assets/background/PfeilOben.png"),
                () -> {
                   erstesOGScene erstes = new erstesOGScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    erstes.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, erstes);
                });

        oben.x = (WIDTH/2) - (oben.width/2);
        oben.y = 4;
        objects.add(oben);
    }
}
