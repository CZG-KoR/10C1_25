package czg.objects;

import czg.scenes.BaseScene;

import java.awt.*;

/**
 * Ein Button-Objekt, bestehend aus einer Position und einem Bild,
 * welches eine gegebene Funktion ausführt, sobald der Button gedrückt wird.
 */
public class ButtonObject extends BaseObject {
    public Runnable method;

    /**
     * Einen neuen Button erstellen und in die Mitte des Bildschirms platzieren.
     * Die Größe des Buttons entspricht der Größe des Bildes.
     * @param sprite Bild
     * @param method auszuführende Methode
     */
    public ButtonObject(Image sprite, Runnable method) {
        super(sprite);
        this.method = method;
    }

    /**
     * Einen neuen Button erstellen und an die angegebene Stelle platzieren.
     * Die Größe des Buttons entspricht der Größe des Bildes.
     * @param sprite Bild
     * @param x X-Position
     * @param y Y-Position
     * @param method auszuführende Methode
     */
    public ButtonObject(Image sprite, int x, int y, Runnable method) {
        super(sprite, x, y);
        this.method = method;
    }

    /**
     * Einen neuen Button erstellen.
     * @param sprite Bild
     * @param x X-Position
     * @param y Y-Position
     * @param width Breite
     * @param height Höhe
     * @param method auszuführende Methode
     */
    public ButtonObject(Image sprite, int x, int y, int width, int height, Runnable method) {
        super(sprite, x, y, width, height);
        this.method = method;
    }

    @Override
    public void update(BaseScene scene) {
        if(isClicked()) {
            method.run();
        }
    }
}