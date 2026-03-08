/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.objects;

import czg.objects.minigame_objects.MinigameNameObject;
import czg.scenes.BaseScene;
import czg.util.Images;

import java.awt.*;

/**
 * Klasse für Items. Jedes Item sollte als <b>eine</b> {@code public static final}-Instanz
 * (Singleton) in dieser Klasse angelegt werden.
 */
public class ItemObject extends BaseObject{

    public final int level;
    public final String name;
    public final int ID;

    private ItemObject(Image sprite, int x, int y, int level, String name, int ID) {
        super(sprite, x, y);
        
        this.level = level;
        this.name = name;
        this.ID = ID;
    }

    /**
     * Gibt ein Item als Belohnung für das Beenden eines Minispiels zurück.
     * @param minigame Das Minispiel, welches beendet wurde
     * @param level Das Level, welches beendet wurde
     */
    public static ItemObject getMinigameReward(MinigameNameObject minigame, int level) {
        return new ItemObject(Images.get("/assets/items/Atom.png"), 0, 0, 0, "tmp", 0); // temporär bitte ändern
    }
    
    @Override
    public void update(BaseScene scene) {
       
    }
    
}
