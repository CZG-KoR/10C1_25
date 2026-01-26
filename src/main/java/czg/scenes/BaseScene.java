package czg.scenes;

import czg.objects.BaseObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Eine Szene besteht aus einem Hintergrund und einer beliebigen Menge von
 * sich darauf bewegenden Objekten.
 */
public class BaseScene {

    /**
     * Liste der Objekte in diese Szene
     */
    public final List<BaseObject> objects = new ArrayList<>();

    /**
     * Ob die Szene verdeckt ist
     */
    public boolean isCovered = false;
    /**
     * Ob die Szene ausgeblendet werden sollte, wenn sie verdeckt ist
     */
    public boolean coverDisablesDrawing = false;
    /**
     * Ob die Szene noch ihren Code ausführen sollte, wenn sie verdeckt ist
     */
    public boolean coverPausesLogic = false;
    /**
     * Ob die Szene ihre Musik oder Effekte pausieren sollte, wenn sie verdeckt ist
     */
    public boolean coverPausesAudio = false;

    /**
     * Alle Objekte abfragen, die mit dem gegebenen überlappen (damit "kollidieren")
     * @param object Das Objekt, welches überprüft werden soll
     * @return Die Liste von kollidierenden Objekten
     */
    public List<BaseObject> getOverlappingObjects(BaseObject object) {
        Rectangle2D refHitbox = object.getHitbox();

        if(refHitbox == null)
            return new ArrayList<>(0);

        return objects.stream()
                .filter(obj -> {
                    if(obj == object)
                        return false;

                    Rectangle2D hitbox = obj.getHitbox();
                    return hitbox != null && hitbox.intersects(refHitbox);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Ruft {@link BaseObject#update(BaseScene)} für jedes Objekt in der {@link #objects}-Liste auf.
     * Übergibt diese Szene als Parameter.
     */
    public void update() {
        // Ähnlich wie bei SceneStack wird hier erst eine Kopie von objects angelegt.
        new ArrayList<>(objects).forEach(object -> object.update(this));
    }


    /**
     * Zeichnet den Hintergrund und alle Objekte der Szene
     * @param g Grafik-Objekt. Wird vom Szenen-Stapel bereitgestellt.
     * @see SceneStack#paintComponent(Graphics)
     */
    public void draw(Graphics2D g) {
        // Objekte zeichnen
        objects.forEach(o -> o.draw(g));
    }
}
