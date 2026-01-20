package czg.scene;

import czg.MainWindow;
import czg.game.objects.BaseObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Eine Szene besteht aus einem Hintergrund und einer beliebigen Menge von
 * sich darauf bewegenden Objekten.
 */
public class Scene {

    /**
     * Ob ein einfarbiger Hintergrund gewünscht ist. Andernfalls
     * wird das {@link #backgroundImage} verwendet.
     */
    private boolean useBackgroundColor = true;
    /**
     * Hintergrundfarbe, falls {@link #useBackgroundColor} {@code true} ist.
     */
    private Color backgroundColor = Color.BLACK;
    /**
     * Hintergrundbild, falls {@link #useBackgroundColor} {@code false} ist.
     */
    private Image backgroundImage = null;

    /**
     * Liste der Objekte in diese Szene
     */
    public final List<BaseObject> objects = new ArrayList<>();

    // TODO tolle sachen mit bit-masken
    /**
     * Ob diese Szene vollständig ausgeblendet werden soll, wenn eine
     * andere über ihr angezeigt wird, oder ob sie immer noch darunter
     * sichtbar sein soll.
     */
    public boolean canBeCovered = false;

    /**
     * Ob diese Szene aktuell von einer anderen verdeckt ist.
     * Sollte nicht von hand gesetzt werden.
     */
    public boolean isCovered = false;

    public void setBackgroundColor(Color c) {
        useBackgroundColor = true;
        backgroundColor = c;
    }

    public void setBackgroundImage(Image i) {
        useBackgroundColor = false;
        backgroundImage = i;
    }

    public void update() {
        if(! isCovered)
            objects.forEach(BaseObject::update);
    }

    public void draw(Graphics2D g) {
        // Hintergrund zeichnen:
        if(useBackgroundColor) {
            // Einfarbig
            g.setColor(backgroundColor);
            g.fillRect(0,0,MainWindow.WIDTH,MainWindow.HEIGHT);
        } else {
            // Bild
            g.drawImage(backgroundImage, 0, 0, MainWindow.WIDTH, MainWindow.HEIGHT, null);
        }

        // Objekte zeichnen
        objects.forEach(o -> o.draw(g));
    }
}
