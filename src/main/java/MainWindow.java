import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private static MainWindow instance = null;

    /**
     * @return Die Instanz des Fensters zugreifen
     */
    public static MainWindow getInstance() {
        return instance;
    }

    private MainWindow() {
        super("CZGame");

        // Elemente werden manuell platziert; Kein Layout-Manager
        this.setLayout(null);

        // Feste Größe
        this.setSize(new Dimension(WIDTH,HEIGHT));
        this.setResizable(false);

        // Gesamtes Programm wird beendet, wenn das Fenster geschlossen wird
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // In die Mitte des Bildschirms platzieren
        this.setLocationRelativeTo(null);

        // Zeigen
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // OpenGL-Grafikschnittstelle und damit (hoffentlich) die Grafikkarte verwenden
        System.setProperty("sun.java2d.opengl","true");

        // Fenster erstellen
        instance = new MainWindow();
    }
    
}
