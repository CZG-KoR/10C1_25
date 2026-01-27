
import java.util.ArrayList;
import java.util.List;

public class Lehrer extends NPC {
    int hp;
    String fachschaft;
    List<Integer> items = new ArrayList<>();
    int level;

    public Lehrer(int x, int y, Image img, String name, int hp, String fachschaft, int level) {
        super(x, y, img, name);
        
    }

    private List<Item> createItemList(int level, String fachschaft) {
        if (level == 1) {
            int[] itemsNachLevel = {1, 1, 1, 2};
        }
        else if (level == 2) {
            int[] itemsNachLevel = {1, 1, 2, 2};
        }
        else {
            int[] itemsNachLevel = {1, 2, 2, 3};
        }

        // Hier muss noch vom Level zur Item Liste 
        
        return // Ist noch nicht fertig
    }

}