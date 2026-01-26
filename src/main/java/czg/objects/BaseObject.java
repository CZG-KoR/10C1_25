package czg.objects;

import czg.MainWindow;
import czg.scenes.BaseScene;
import czg.util.Input;
import czg.util.Input.KeyState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static czg.MainWindow.*;

/**
 * Ein minimales Spiel-Objekt, bestehend aus einer Position und einem Bild.
 */
public abstract class BaseObject {

    public int x, y;
    public int width, height;
    public Image sprite;

    /**
     * Ein neues Objekt erstellen und in die Mitte des Bildschirms platzieren.
     * Die Größe des Objekts entspricht der Größe des Bildes.
     * @param sprite Bild
     */
    protected BaseObject(Image sprite) {
        this(sprite, WIDTH / 2, HEIGHT / 2);
    }

    /**
     * Ein neues Objekt erstellen und an die angegebene Stelle platzieren.
     * Die Größe des Objekts entspricht der Größe des Bildes.
     * @param sprite Bild
     * @param x X-Position
     * @param y Y-Position
     */
    protected BaseObject(Image sprite, int x, int y) {
        this(sprite, x, y,
                sprite.getWidth(null) * PIXEL_SCALE,
                sprite.getHeight(null) * PIXEL_SCALE);
    }

    /**
     * Ein neues Objekt erstellen.
     * @param sprite Bild
     * @param x X-Position
     * @param y Y-Position
     * @param width Breite
     * @param height Höhe
     */
    protected BaseObject(Image sprite, int x, int y, int width, int height) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Die {@link Rectangle2D}-Klasse bietet viele nützliche Funktionen wie {@link Rectangle2D#contains(Point2D)} und
     * {@link Rectangle2D#contains(Rectangle2D)}. So kann geprüft werden, ob ein Objekt angeklickt wurde oder ob
     * es sich auf einem anderen Objekt befindet (dieses "berührt").
     * Unterklassen von {@code BaseObject} können diese Methode auch überschreiben, <b>also auch {@code null} zurückgeben.</b>
     * @return Die Position sowie Größe des Objektes verpackt in ein {@link Rectangle2D}
     */
    public Rectangle2D getHitbox() {
        return new Rectangle2D.Float(x, y, width, height);
    }

    /**
     * Ein Objekt gilt als angeklickt, wenn sich der Mauszeiger über diesem befindet
     * und die linke Maustaste geklickt wurde ({@link KeyState#PRESSED}).
     * @return Ob das Objekt angeklickt wurde
     */
    protected boolean isClicked() {
        return MainWindow.getInstance().getMousePosition() != null
                && getHitbox().contains(MainWindow.getInstance().getMousePosition())
                && Input.INSTANCE.getMouseState(MouseEvent.BUTTON1) == Input.KeyState.PRESSED;
    }

    /**
     * Zeichnet das Bild des Objektes
     * @param g Grafik-Objekt. Von der Szene bereitgestellt.
     */
    public void draw(Graphics2D g) {
        // Überspringen, wenn kein Sprite gesetzt ist
        if(sprite == null)
            return;

        g.drawImage(
                sprite,
                x, y,
                width, height,
                null
        );
    }

    /**
     * Logik des Objektes. Muss von einer Unterklasse implementiert werden.
     * @param scene Die Szene, in welcher sich das Objekt befindet
     */
    public abstract void update(BaseScene scene);

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
