/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.objects;

//Importieren nötiger Methoden
import czg.scenes.BaseScene;
import czg.sound.BaseSound;
import czg.sound.ClipSound;
import czg.sound.EndOfFileBehaviour;
import czg.util.Images;
import czg.util.Input;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author guest-0wphjm
 */
public class PlayerObject extends BaseObject{
    //Festlegen der benötigten Variablen (Lebenspunkte, veränderliche Charaktereigentschaften)
    public String name;
    //Anlegen einer Reihung "Inventar", in welchem die Items, auf die der Spieler zugreifen kann, gespeichert werden
    public List<ItemObject> inventar;

    public static PlayerObject INSTANCE = new PlayerObject(Images.get("/assets/characters/PlayerBase.png"), 0, 0, "tmp", new ArrayList<>());

    public PlayerObject() {
        super(Images.get("/assets/characters/PlayerBase.png"));
    }
    
    
    public PlayerObject(Image sprite, int x, int y, String name, ArrayList<ItemObject> inventar){
          super (sprite, x, y);
          this.name = name;
          this.inventar = inventar;
          
      
    }
    //Sobald der Spieler angeklickt wird, wird das Inventar geöffnet
    /*
    @Override
    public void update(BaseScene scene) {
        if(isClicked())
            SceneStack.INSTANCE.push(new InventarScene());
    }
     */
    
}
