package czg.scenes;

import czg.scenes.cover_settings.Rules;
import czg.scenes.cover_settings.Setting;
import czg.util.Input;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Der Szenen-Stapel enthält beliebig viele Szenen (siehe {@link BaseScene}), die übereinander
 * angezeigt werden. Dabei kann, dem Stapel-Modell folgend, immer nur eine neue Szene
 * über allen anderen hinzugefügt, sowie nur die oberste Szene entfernt werden.
 * <br><b>WICHTIG:</b> Wird in einem Durchlauf eine Szene entfernt, wird deren {@link BaseScene#update()}-Methode möglicherweise
 * trotzdem noch einmal ausgeführt, da der nun geänderte Szenen-Stapel erst beim nächsten durchlauf
 * von der {@link #update()} berücksichtigt wird.
 */
public class SceneStack extends JPanel {

    /**
     * Die einzige Instanz des Szenen-Stapels
     */
    public static final SceneStack INSTANCE = new SceneStack();

    private SceneStack() {
        setDoubleBuffered(true);

        // Tastatur- und Maus-Eingaben empfangen
        addKeyListener(Input.INSTANCE);
        addMouseListener(Input.INSTANCE);
        addMouseMotionListener(Input.INSTANCE);
        addFocusListener(Input.INSTANCE);
    }

    /**
     * Eigene Liste mit Szenen weil contentPane.getComponents()
     */
    private final List<BaseScene> scenes = new ArrayList<>();

    /**
     * Cache, der für jeden Index in der {@link #scenes}-Liste eine Liste
     * mit den Tags von allen darüber liegenden Szenen speichert
     */
    private final List<SequencedMap<String,Integer>> overlyingTags = new ArrayList<>();

    private static final SequencedSet<String> EMPTY_TAG_SET = new LinkedHashSet<>();

    /**
     * Aktualisiert {@link #overlyingTags}, indem die Tags der Szene an Stelle {@code fromIndex}
     * im Stapel genommen werden und die jeweils zugeordneten Zahlen in den Cache-Maps der darunterliegenden
     * Szenen um {@code counterDelta} ändert
     * @param fromIndex Die Cache-Maps aller Szenen unter diesem Index werden aktualisiert
     * @param counterDelta Um wie viel die Counter geändert werden sollen
     */
    private void propagateOverlyingTags(int fromIndex, int counterDelta) {
        assert overlyingTags.size() == scenes.size();

        // Tags nehmen
        SequencedSet<String> tags = scenes.get(fromIndex).tags.getOrDefault(EMPTY_TAG_SET);

        // Über alle Indices < fromIndex gehen
        for (int i = fromIndex-1; i >= 0; i--) {
            // Map nehmen
            Map<String,Integer> overlying = overlyingTags.get(i);

            // Zähler für die Tags aktualisieren
            for(String t : tags) {
                int newCount = overlying.getOrDefault(t, 0) + counterDelta;

                if(newCount > 0)
                    overlying.put(t, newCount);
                else {
                    overlying.remove(t);
                }
            }
        }

    }

    /**
     * Zeigt eine weitere Szene über allen bestehenden Szenen an
     * @param scene Beliebige Szene
     */
    public void push(BaseScene scene) {
        // Ggf. letzte Szene verdecken
        BaseScene last = getTop();
        if(last != null) {
            last.setCovered(true);
        }

        // Neues Set für Tags anlegen
        overlyingTags.add(new LinkedHashMap<>());
        // In die Liste aufnehmen
        scenes.add(scene);
        // Der Szene ihre Position mitteilen
        scene.sceneStackPosition = scenes.size()-1;
        // Cache aktualisieren: Counter für die Tags der neuen Szene bei den darunterliegenden Szenen erhöhen
        propagateOverlyingTags(scenes.size()-1, 1);

    }

    /**
     * Entfernt die oberste Szene
     */
    public void pop() {
        BaseScene last = getTop();
        if(last != null) {
            // Cache aktualisieren: Tag-Counter der Tags der entfernten Szene bei allen Szenen im Stapel dekrementieren
            propagateOverlyingTags(scenes.size()-1, -1);
            // Map mit Tags entfernen
            overlyingTags.removeLast();
            // Aus der Liste entfernen & unload() ausführen
            scenes.removeLast().unload();

            // last-Variable Aktualisieren
            last = getTop();
            // Nicht mehr bedecken
            if(last != null)
                last.setCovered(false);
        } else
            System.err.println("Es wurde versucht, eine Szene zu entfernen, obwohl keine Szenen mehr auf dem Stapel sind!");
    }

    /**
     * Fügt {@code szene} in den Stapel ein, sodass {@code scenes.get(i) == scene}.
     * Alle Szenen ab der, die vorher an der Stelle war, an welcher die neue Szene
     * eingefügt wird, werden um eine Stelle nach hinten/oben geschoben.
     * @param scene Einzufügende Szene
     * @param index Stelle, an welcher die Szene eingefügt werden soll. Zwischen {@code 0} und {@code scenes.size()}, jeweils inklusiv.
     */
    public void insert(BaseScene scene, int index) {
        // Ganz oben einfügen: An push() weitergeben
        if(index >= scenes.size()) {
            push(scene);
            return;
        }

        // Ungültiger Index: Fehlermeldung
        if(index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        // Neue Map für die neu eingefügte Szene einfügen
        if(index == 0) {
            // → Ganz unten in den Stapel einfügen
            // Tags von der Szene kopieren, die vorher ganz unten war (und jetzt zu Index 1 geschoben wurde)
            overlyingTags.addFirst(new LinkedHashMap<>(overlyingTags.getFirst()));
            // In die Szenen-Liste einfügen
            scenes.addFirst(scene);
            // Die Tags der Szene, die jetzt an Index 1 geschoben wurde, zu der neu eingefügten Szene hinzufügen
            propagateOverlyingTags(1, 1);
        } else {
            // → Nicht ganz unten in den Stapel einfügen
            // Tags von der Szene kopieren, die vorher unter der Szene lag, unter der
            // jetzt die neu eingefügte Szene ist
            overlyingTags.addFirst(new LinkedHashMap<>(overlyingTags.get(index-1)));
            // In die Szenen-Liste einfügen
            scenes.add(index, scene);
            // Die Tags der neu eingefügten Szene zu den darunterliegenden hinzufügen
            propagateOverlyingTags(index, 1);
        }

        // Den Szenen ihre neuen Positionen mitteilen
        for(int i = index; i < scenes.size(); i++)
            scenes.get(i).sceneStackPosition = i;

    }

    /**
     * Findet die erste Szene {@code s} im Stapel, für die {@code s == toBeReplaced} gilt,
     * und ersetzt diese durch {@code replacement}
     * @param toBeReplaced Zu ersetzende Szene
     * @param replacement Szene, die an die Stelle der zu ersetzenden Szene tritt
     */
    public void replace(BaseScene toBeReplaced, BaseScene replacement) {
        for (int i = 0; i < scenes.size(); i++) {
            // Index von toBeReplaced suchen
            if(scenes.get(i) == toBeReplaced) {
                // Counter für die Tags der ersetzten Szene dekrementieren
                propagateOverlyingTags(i, -1);

                // Wir können für die Szene, die gerade eingefügt wurde, einfach
                // die bestehende Map bei overlyingTagsCache[i] verwenden!

                // Szene ersetzen
                scenes.set(i, replacement);

                // Der eingefügten Szene ihre Position mitteilen
                replacement.sceneStackPosition = i;

                // Counter für die Tags der neu eingefügten Szene inkrementieren
                propagateOverlyingTags(i, 1);

                // unload() für die ersetzte Szene ausführen
                toBeReplaced.unload();

                return;
            }
        }

        System.err.printf("Es wurde versucht, %s im Szenen-Stapel zu ersetzten, obwohl diese Szene nicht darin vorkommt%n", toBeReplaced);
    }

    /**
     * Findet die erste Szene {@code s} im Stapel, für die {@code s == toBeRemoved} gilt,
     * und entfernt diese. Ggf. wird {@link BaseScene#isCovered} der darunterliegenden Szene
     * auf {@code false} gesetzt.
     * @param toBeRemoved Die zu entfernende Szene
     */
    public void remove(BaseScene toBeRemoved) {
        if(toBeRemoved == scenes.getLast()) {
            // An pop() weitergeben
            pop();
            return;
        }

        for(int i = 0; i < scenes.size(); i++) {
            if(scenes.get(i) == toBeRemoved) {
                // Counter für die Tags der zu entfernenden Szene dekrementieren
                propagateOverlyingTags(i, -1);

                // Map entfernen
                overlyingTags.remove(i);

                // Aus der scenes-Liste entfernen & unload() ausführen
                scenes.remove(i).unload();

                // Den nach unten gerutschten Szenen ihre neuen Positionen mitteilen
                for(int j = i; j < scenes.size(); j++)
                    scenes.get(j).sceneStackPosition = j;

                return;
            }
        }

        System.err.printf("Es wurde versucht, %s aus dem Szenen-Stapel zu entfernen, obwohl diese Szene nicht darin vorkommt%n", toBeRemoved);
    }


    /**
     * setzt {@link BaseScene#isCovered} für alle Szenen im Stapel auf {@code false},
     * leert den {@link #overlyingTags} und entfernt alle Szenen vom Stapel.
     */
    public void clear() {
        scenes.forEach(scene -> {
            scene.setCovered(false);
            scene.unload();
        });
        overlyingTags.clear();
        scenes.clear();
    }


    /**
     * Szene ganz oben auf dem Stapel abfragen
     * @return Szene oben auf dem Stapel
     */
    public BaseScene getTop() {
        return scenes.isEmpty() ? null : scenes.getLast();
    }


    /**
     * Iteriert über {@link #scenes} bzw. eine Kopie davon. Ermittelt für
     * jede Szene die effektiven {@link Rules} und
     * bestimmt mithilfe der {@code settingExtractor}-Funtktion, ob der
     * {@code processor} für diese Szene aufgerufen werden soll.
     * @param settingExtractor {@link Function}, die eine {@link Setting} aus einem Satz {@link Rules} abfragt
     * @param processor {@link Consumer}, der ggf. auf die Szene angewendet wird
     * @param useCopy Ob eine Kopie von {@link #scenes} oder die Liste selbst verwendet werden soll
     */
    private void processScenes(
            Function<Rules, Setting> settingExtractor,
            Consumer<BaseScene> processor,
            boolean useCopy
    ) {
        // Es wird mit einer Kopie der scenes-Liste gearbeitet, da diese
        // sich ändern kann (z.B. durch Code in den update()-Methoden von
        // Objekten), während sie hier eigentlich noch durchlaufen wird.
        // Würde hier die scenes-Liste direkt benutzt werden, könnte es
        // so zu einer ConcurrentModificationException kommen!
        List<BaseScene> iterSceneList = useCopy ? new ArrayList<>(scenes) : scenes;
        List<SequencedMap<String,Integer>> iterOverlyingTagsList =
                useCopy ? overlyingTags.stream().map(
                        map -> (SequencedMap<String, Integer>) new LinkedHashMap<>(map))
                            .toList()
                        : overlyingTags;

        for (int i = 0; i < iterSceneList.size(); i++) {
            BaseScene scene = iterSceneList.get(i);

            boolean filterSetting = settingExtractor.apply(
                    scene.coverSettings.getEffectiveRules(iterOverlyingTagsList.get(i).sequencedKeySet())
            ).toBoolean();

            if(! (scene.isCovered() && filterSetting))
                processor.accept(scene);
        }
    }

    /**
     * Gibt die Vereinigungsmenge aller Tags von allen Szenen über der
     * Szene am gegebenem Index im Stapel zurück. <b>Wichtig:</b> Änderungen,
     * die während {@link #update()} entstanden sind, werden durch diese Funktion
     * erst beim nächsten Durchlauf sichtbar!
     * @param index Index der Szene im Stapel
     * @return Set der Tags aller darüberliegenden Szenen
     */
    public SequencedSet<String> getOverlyingTags(int index) {
        return Collections.unmodifiableSequencedSet(overlyingTags.get(index).sequencedKeySet());
    }

    /**
     * Logik-Code der einzelnen Szenen ausführen
     */
    public void update() {
        processScenes(Rules::coverPausesLogic, BaseScene::update, true);
    }


    /**
     * Von außen erreichbare Zeichen-Funktion. Für Unit-Tests gedacht.
     * @param g Normalerweise von {@link #paintComponent(Graphics)} bereitgestellt. Unit-Test sollten hier {@code null} übergeben.
     */
    public void draw(@Nullable Graphics2D g) {
        final Graphics2D g2 = g == null ? new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB).createGraphics() : g;

        // Anti-aliasing aktivieren (Text nicht pixelig darstellen)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Alle Szenen zeichnen, die nicht verdeckt und so eingestellt sind,
        // dass sie deshalb ausgeblendet sein sollte.
        processScenes(Rules::coverDisablesDrawing, scene -> scene.draw(g2), false);
    }

    /**
     * Zeichnet alle Szenen des Stapels. Die erste Szene in der Liste wird zuerst,
     * die letzte zuletzt, also über allen anderen, gezeichnet.
     * @param graphics Grafik-Objekt. Wird von Java bereitgestellt.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw((Graphics2D) graphics);
    }

}
