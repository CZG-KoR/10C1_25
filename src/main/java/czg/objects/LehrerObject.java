package czg.objects;

import czg.scenes.BaseScene;

import java.awt.*;
import java.util.Random;

public class LehrerObject extends BaseObject{

    public final int LEVEL;
    public int hp;
    public final String FACHSCHAFT;
    public int[] lehrer_items;

    public LehrerObject(Image sprite, int x, int y, String FACHSCHAFT, int hp, int LEVEL, int[] lehrer_items) {
        super(sprite, x, y);
        this.LEVEL = LEVEL;
        this.hp = hp;
        this.FACHSCHAFT = FACHSCHAFT;
        this.lehrer_items = lehrer_items;
    }

    public void verteidigung(int level) {
        // Es wird random ausgewählt, ob ein Item gewählt wird (und welches), oder ob der Lehrer nichts macht.
        Random rand = new Random();
        int move = rand.nextInt(5);
        int schaden;
        int item_lehrer;

        if (move == 0) {
            schaden = level;
            item_lehrer = -1;
        }
        else {
            item_lehrer = lehrer_items[move - 1];
//            schaden = level - Integer.parseInt(Itemobject.testitemliste[item_lehrer][1]);
//            if (schaden <= 0) {
//                schaden = 0;
//            }
//
        }
//
//        hp -= schaden;

    }

    @Override
    public void update(BaseScene scene) {
    }
}
