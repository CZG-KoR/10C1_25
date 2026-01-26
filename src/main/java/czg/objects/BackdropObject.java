package czg.objects;

import czg.scenes.BaseScene;
import czg.util.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;

/**
 * Ein Objekt mit der Größe des gesamten Bildschirms, welches als
 * Hintergrund für Szenen dienen kann.
 */
public class BackdropObject extends BaseObject {

    /**
     * Ob ein einfarbiger Hintergrund gewünscht ist. Andernfalls
     * wird der {@link #sprite} verwendet.
     */
    private boolean useBackgroundColor;
    /**
     * Hintergrundfarbe, falls {@link #useBackgroundColor} {@code true} ist.
     */
    private Color backgroundColor;


    /**
     * Ein Bild als Hintergrund verwenden
     * @param background Hintergrundbild
     */
    public BackdropObject(Image background) {
        super(null, 0, 0, WIDTH, HEIGHT);
        setBackgroundImage(background);
    }

    /**
     * Eine Farbe als Hintergrund verwenden
     * @param background Hintergrundfarbe
     */
    public BackdropObject(Color background) {
        super(null, 0, 0, WIDTH, HEIGHT);
        setBackgroundColor(background);
    }

    /**
     * Eine einzelne Farbe als Hintergrund verwenden
     * @param c Die Hintergrundfarbe
     */
    public void setBackgroundColor(Color c) {
        useBackgroundColor = true;
        backgroundColor = c;
    }

    /**
     * Ein Bild als Hintergrund verwenden
     * @param i Das Bild
     * @see Images#get(String)
     */
    public void setBackgroundImage(Image i) {
        useBackgroundColor = false;
        sprite = i;
    }

    /**
     * Der Hintergrund hat keine Hitbox, wird als in Kollisions-Überprüfungen ignoriert.
     * @return {@code null}
     */
    @Override
    public Rectangle2D getHitbox() {
        return null;
    }

    @Override
    public void update(BaseScene scene) {}

    @Override
    public void draw(Graphics2D g) {
        // Hintergrund zeichnen:
        if(useBackgroundColor) {
            // Einfarbig
            g.setColor(backgroundColor);
            g.fillRect(0,0, width, height);
        } else {
            // Bild
            super.draw(g);
        }
    }
}
