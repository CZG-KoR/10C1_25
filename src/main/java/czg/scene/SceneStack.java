package czg.scene;

import czg.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Der Szenen-Stapel enthält beliebig viele Szenen (siehe {@link Scene}), die übereinander
 * angezeigt werden. Dabei kann, dem Stapel-Modell folgend, immer nur eine neue Szene
 * über allen anderen hinzugefügt, sowie nur die oberste Szene entfernt werden.
 */
public class SceneStack extends JPanel {

    /**
     * Eigene Liste mit Szenen weil contentPane.getComponents()
     */
    private final List<Scene> scenes = new ArrayList<>();

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
    public void push(Scene scene) {
        // Ggf. letzte Szene verdecken
        Scene last = getTop();
        if(last != null && last.canBeCovered) {
            last.isCovered = true;
        }

        // In die Liste aufnehmen
        scenes.add(scene);
    }

    /**
     * Entfernt die oberste Szene
     */
    public void pop() {
        Scene last = getTop();
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
     * Logik-Code der einzelnen Szenen ausführen
     */
    public void update() {
        scenes.forEach(Scene::update);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics;

        // Alle Szenen zeichnen
        for(Scene scene : scenes)
            scene.draw(g2);
    }

    /**
     * @return Szene oben auf dem Stapel
     */
    private Scene getTop() {
        return scenes.isEmpty() ? null : scenes.get(scenes.size()-1);
    }

}
