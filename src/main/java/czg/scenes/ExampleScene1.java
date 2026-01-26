package czg.scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.ExampleDoorObject;
import czg.util.Draw;

import java.awt.*;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;

public class ExampleScene1 extends BaseScene {

    public ExampleScene1() {
        objects.add(new BackdropObject(new Color(130, 149, 163)));

        ExampleDoorObject door = new ExampleDoorObject(0, 0, ExampleScene2::new);
        door.x = WIDTH - Math.round(door.width * 1.5f);
        door.y = HEIGHT / 2 - door.height / 2;
        objects.add(door);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        g.setFont(Draw.FONT_TITLE);
        g.setColor(Color.BLACK);
        Draw.drawTextCentered(g, "Szene 1", MainWindow.WIDTH / 2, 60);
    }
}
