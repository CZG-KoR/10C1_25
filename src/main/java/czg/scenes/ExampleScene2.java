package czg.scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.ExampleDoorObject;
import czg.util.Draw;
import czg.util.Images;

import java.awt.*;

import static czg.MainWindow.HEIGHT;

public class ExampleScene2 extends BaseScene {
    public ExampleScene2() {
        objects.add(new BackdropObject(Images.get("/assets/background/example.png")));

        ExampleDoorObject door = new ExampleDoorObject(0, 0, ExampleScene1::new);
        door.x = Math.round(door.width * 0.5f);
        door.y = HEIGHT / 2 - door.height / 2;
        objects.add(door);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        g.setFont(Draw.FONT_TITLE);
        g.setColor(Color.MAGENTA);
        Draw.drawTextCentered(g, "Szene 2", MainWindow.WIDTH / 2, 60);
    }
}
