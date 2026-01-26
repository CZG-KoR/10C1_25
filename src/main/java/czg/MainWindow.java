package czg;

import czg.objects.ExamplePlayerObject;
import czg.scenes.ExampleScene1;
import czg.scenes.SceneStack;
import czg.util.Input;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements Runnable {

    /**
     * Wie viele Bildschirm-Pixel ein Textur-Pixel beansprucht
     */
    public static final int PIXEL_SCALE = 6;

    /**
     * Wie viele Bildschirm-Pixel das Fenster breit ist
     */
    public static final int WIDTH = 140 * PIXEL_SCALE;

    /**
     * Wie viele Bildschirm-Pixel das Fenster hoch ist
     */
    public static final int HEIGHT = 105 * PIXEL_SCALE;

    /**
     * Einzelbilder pro Sekunde
     */
    public static final int FPS = 30;

    private static MainWindow INSTANCE = null;

    /**
     * Die Instanz des Szenen-Stapels
     */
    public final SceneStack SCENE_STACK;

    /**
     * @return Die Instanz des Fensters zugreifen
     */
    public static MainWindow getInstance() {
        return INSTANCE;
    }

    private MainWindow() {
        super("CZGame");

        // Feste Größe
        setSize(new Dimension(WIDTH,HEIGHT));
        setResizable(false);

        // Manuelles platzieren von Elementen
        setLayout(null);

        // Szenen-Stapel hinzufügen
        SCENE_STACK = new SceneStack();
        setContentPane(SCENE_STACK);

        // Tastatur- und Maus-Eingaben empfangen
        addKeyListener(Input.INSTANCE);
        addMouseListener(Input.INSTANCE);

        // Gesamtes Programm wird beendet, wenn das Fenster geschlossen wird
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // In die Mitte des Bildschirms platzieren
        setLocationRelativeTo(null);

        // Zeigen
        setVisible(true);
    }

    public static void main(String[] args) {
        // OpenGL-Grafikschnittstelle und damit (hoffentlich) die Grafikkarte verwenden
        System.setProperty("sun.java2d.opengl","true");

        // Fenster erstellen
        INSTANCE = new MainWindow();

        // Haupt-Schleife in einem neuen Thread starten
        new Thread(INSTANCE).start();

        // BEISPIEL-SZENE (nur zur Referenz, später entfernen!)
        ExampleScene1 s1 = new ExampleScene1();
        s1.objects.add(ExamplePlayerObject.INSTANCE);
        INSTANCE.SCENE_STACK.push(s1);
    }

    /**
     * Haupt-Logik
     */
    @Override
    public void run() {
        // In welchem Zeitintervall (in Nanosekunden) die Spiellogik ausgeführt werden soll
        final double interval = 1e9 / FPS;
        // Zählt, wie oft die Spiellogik durchlaufen werden sollte.
        double delta = 0;

        // Zeit seit dem letzten Durchlauf der while(true)-Schleife
        long lastTime = System.nanoTime();
        // Zweite Zeit-Variable. Wird zur neuen lastTime.
        long currentTime;

        System.out.println("Haupt-Schleife beginnt");

        while(true) {
            // Aktuelle Zeit messen
            currentTime = System.nanoTime();

            // Die Zeit berechnen, die seit dem letzten Durchlauf dieser Schleife vergangen ist.
            // Damit berechnen, wie oft die Spiellogik in dieser Zeitspanne hätte durchlaufen
            // werden sollen (normalerweise eine Anzahl <1).
            delta += (currentTime - lastTime) / interval;

            // Die currentTime wird wieder zur lastTime
            lastTime = currentTime;

            // Alle nötigen Durchläufe abarbeiten
            while(delta >= 1) {
                // Code für Szenen und Objekte ausführen
                SCENE_STACK.update();
                // Zuvor nur als KeyState.PRESSED eingetragene Tasten
                // jetzt als KeyState.HELD behandeln
                Input.INSTANCE.updatePressedToHeld();
                // Grafik
                SCENE_STACK.repaint();

                // Durchlauf abgeschlossen, Zähler kann um 1 verringert werden
                delta--;
            }

        }

    }
}
