package czg.objects;

import czg.scenes.BaseScene;

import java.awt.*;

public class KampfPlayerObject extends BaseObject{

    private final int hp;
    private final int[] items;

    public KampfPlayerObject(Image sprite, int x, int y, int hp, int[] items) {
        super(sprite, x, y);
        this.hp = hp;
        this.items = items;
    }

    @Override
    public void update(BaseScene scene) {
    }

    /*

    public void angriff() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID?");
        int ausgewaehlt = Integer.parseInt(scanner.nextLine());
        int level = Integer.parseInt(ItemObject.testitemliste[ausgewaehlt][1]);

        int[] testitems = {0, 1, 2};
        LehrerObject lehrer = new LehrerObject(Images.get("/assets/characters/bre.png"), 10, 20, "Physik", 10, 2, testitems);
        lehrer.verteidigung(level);
    }

     */


}
