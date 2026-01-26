package czg.scenes;

import czg.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
     * Eigene Liste mit Szenen weil contentPane.getComponents()
     */
    private final List<BaseScene> scenes = new ArrayList<>();

    /**
     * Einen neuen Szenen-Stapel erstellen.
     */
    public SceneStack() {
        // Gesamten Raum ausfüllen
        setBounds(0,0, MainWindow.WIDTH,MainWindow.HEIGHT);
    }

    /**
     * Zeigt eine weitere Szene über allen bestehenden Szenen an
     * @param scene Beliebige Szene
     */
    public void push(BaseScene scene) {
        // Ggf. letzte Szene verdecken
        BaseScene last = getTop();
        if(last != null) {
            last.isCovered = true;
        }

        // In die Liste aufnehmen
        scenes.add(scene);
    }

    /**
     * Entfernt die oberste Szene
     */
    public void pop() {
        BaseScene last = getTop();
        if(last != null) {
            // Aus der Liste entfernen
            scenes.remove(scenes.size()-1);

            // Aktualisieren
            last = getTop();
            // Nicht mehr bedecken
            if(last != null)
                last.isCovered = false;
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

        // Irgendwo innerhalb des Stapels einfügen
        if(index >= 0) {
            scenes.add(index, scene);
            return;
        }

        System.err.printf("Kann keine Szene an Stelle %d einfügen%n", index);
    }

    /**
     * Findet die erste Szene {@code s} im Stapel, für die {@code s == toBeReplaced} gilt,
     * und ersetzt diese durch {@code replacement}
     * @param toBeReplaced Zu ersetzende Szene
     * @param replacement Szene, die an die Stelle der zu ersetzenden Szene tritt
     */
    public void replace(BaseScene toBeReplaced, BaseScene replacement) {
        for (int i = 0; i < scenes.size(); i++) {
            // toBeReplaced suchen...
            if(scenes.get(i) == toBeReplaced) {
                // ... und ersetzen
                scenes.set(i, replacement);
                return;
            }
        }

        System.err.printf("Es wurde versucht, %s im Szenen-Stapel zu ersetzten, obwohl diese Szene nicht darin vorkommt%n", toBeReplaced);
    }

    /**
     * @return Szene oben auf dem Stapel
     */
    private BaseScene getTop() {
        return scenes.isEmpty() ? null : scenes.get(scenes.size()-1);
    }

    /**
     * Logik-Code der einzelnen Szenen ausführen. Szenen die verdeckt sind
     * {@link BaseScene#isCovered} und für die {@link BaseScene#coverPausesLogic}
     * {@code true} ist, werden nicht beachtet.
     */
    public void update() {
        // Es wird mit einer Kopie der scenes-Liste gearbeitet, da diese
        // sich ändern kann (z.B. durch Code in den update()-Methoden von
        // Objekten), während sie hier eigentlich noch durchlaufen wird.
        // Würde hier die scenes-Liste direkt benutzt werden, könnte es
        // so zu einer ConcurrentModificationException kommen!
        new ArrayList<>(scenes).stream()
                .filter(scene -> !(scene.isCovered && scene.coverPausesLogic))
                .forEach(BaseScene::update);
    }

    /**
     * Zeichnet alle Szenen des Stapels. Die erste Szene in der Liste wird zuerst,
     * die letzte zuletzt, also über allen anderen, gezeichnet.
     * @param graphics Grafik-Objekt. Wird von Java bereitgestellt.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics;

        // Anti-aliasing aktivieren (Text nicht pixelig darstellen)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Alle Szenen zeichnen, die nicht verdeckt und so eingestellt sind,
        // dass sie deshalb ausgeblendet sein sollte.
        scenes.stream()
                .filter(scene -> !(scene.isCovered && scene.coverDisablesDrawing))
                .forEach(scene -> scene.draw(g2));
    }

}
