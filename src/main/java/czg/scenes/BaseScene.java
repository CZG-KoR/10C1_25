package czg.scenes;

import czg.objects.BaseObject;
import czg.scenes.cover_settings.CoverSettings;
import czg.sound.SoundGroup;
import czg.util.Lazy;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedSet;
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
     * Sounds in dieser Szene
     */
    public final Lazy<SoundGroup> sounds = new Lazy<>(SoundGroup::new);


    /**
     * Liste der Tags, die diese Szene hat. <b>Sollte nur beim Erstellen der Szene
     * (d.h. im Konstruktor) festgelegt werden.</b>
     */
    public final Lazy<SequencedSet<String>> tags = new Lazy<>(LinkedHashSet::new);

    /**
     * Ob im Szenen-Stapel eine andere Szene über dieser liegt
     */
    protected boolean isCovered = false;

    /**
     * <b>Wird vom {@link SceneStack} gesetzt, nicht selbst anfassen!!</b>
     * An welcher Position im Szenen-Stapel sich diese Szene befindet. Wird
     * auf {@code -1} gesetzt, wenn gar nicht.
     */
    public int sceneStackPosition = -1;

    /**
     * Einstellungen, wenn die Szene von anderen verdeckt ist
     */
    public final CoverSettings coverSettings;

    /**
     * Szene erstellen. Alle {@code CoverSettings}-Einstellungen werden
     * auf {@code false} gesetzt.
     */
    public BaseScene() {
        this(new CoverSettings(false, true, false));
    }

    /**
     * Szene mit {@code CoverSettings} erstellen. Das Erstellen dieser
     * Einstellungen kann über verkettete Funktionsaufrufe erfolgen. Siehe
     * Dokumentation ({@code Fenster_Szenen_Objekte.md}).
     * @param coverSettings {@code CoverSettings}-Objekte
     */
    public BaseScene(CoverSettings coverSettings) {
        this.coverSettings = coverSettings;
    }

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
     * Die Szene verdecken bzw. nicht mehr verdecken
     * @param covered Ob die Szene ab jetzt als verdeckt gilt oder nicht
     */
    public void setCovered(boolean covered) {
        if(covered == isCovered)
            return;

        if((isCovered = covered) && coverSettings.getEffectiveRules(SceneStack.INSTANCE.getOverlyingTags(sceneStackPosition)).coverPausesAudio().toBoolean()) {
            // Bedecken
            sounds.ifPresent(SoundGroup::pause);
        } else {
            // Nicht mehr bedecken
            sounds.ifPresent(SoundGroup::resume);
        }
    }

    /**
     * Abfragen, ob im Szenen-Stapel eine andere Szene auf dieser liegt
     * @return Ob die Szene verdeckt ist
     */
    public boolean isCovered() {
        return isCovered;
    }

    /**
     * Wird aufgerufen, wenn die Szene aus dem Szenen-Stapel entfernt wird
     */
    public void unload() {
        // Position im Szenen-Stapel vergessen
        sceneStackPosition = -1;
        // Ggf. Sounds stoppen
        sounds.ifPresent(SoundGroup::removeAllSounds);
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

    @Override
    public String toString() {
        return getClass().getTypeName();
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
