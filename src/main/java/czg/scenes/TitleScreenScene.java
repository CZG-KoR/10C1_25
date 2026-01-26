package czg.scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.util.Draw;

import java.awt.*;

// TODO: Menüsystem

/**
 * Das Hauptmenü des Spiels sowie die erste angezeigte Szene.
 */
public class TitleScreenScene extends BaseScene {

    public TitleScreenScene() {
        objects.add(new BackdropObject(new Color(52, 48, 48)));
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        g.setFont(Draw.FONT_TITLE);
        g.setColor(Color.WHITE);

        Draw.drawTextCentered(g, "CZG Game", MainWindow.WIDTH / 2, 64);
    }
}
