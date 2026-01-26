package czg.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static czg.MainWindow.PIXEL_SCALE;

/**
 * Zentraler Zugriff auf Bilddateien im "src/main/resources"-Ordner
 */
public class Images {

    private static final Map<String, Image> loaded = new HashMap<>();

    /**
     * Bild, welches verwendet wird für den Fall, dass das eigentliche
     * Bild nicht geladen werden kann. Ein 2x2-Schachbrett-Muster in
     * Schwarz und Magenta (wie in z.B. Minecraft).
     */
    static final Image missingTexture;

    static {
        // missingTexture generieren
        missingTexture = new BufferedImage(PIXEL_SCALE, PIXEL_SCALE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) missingTexture.getGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0,0,PIXEL_SCALE/2,PIXEL_SCALE/2);

        g.setColor(Color.MAGENTA);
        g.fillRect(PIXEL_SCALE/2,0,PIXEL_SCALE/2,PIXEL_SCALE/2);
        g.fillRect(0,PIXEL_SCALE/2,PIXEL_SCALE/2,PIXEL_SCALE/2);
    }

    /**
     * Das Bild "src/main/resources/[path]" ggf. laden und zurückgeben
     * @param path Dateipfad, z.B. "/assets/items/taschenrechner.png". Der Slash vorne ist wichtig!
     * @return Das geladene Bild
     */
    public static Image get(String path) {
        return loaded.computeIfAbsent(path, p -> {
            try(InputStream stream = Images.class.getResourceAsStream(p)) {
                // Datei nicht gefunden → missingTexture
                if(stream == null) {
                    System.err.printf("Datei nicht gefunden: %s%n", p);
                    return missingTexture;
                }

                // Versuchen, das Bild zu laden. Die mögliche IOException
                // wird abgefangen und resultiert in der missingTexture.
                Image result = ImageIO.read(stream);
                // Konnte das Bild nicht gelesen werden, wird ebenfalls die
                // missingTexture zurückgegeben.
                return result == null ? missingTexture : result;
            } catch (IOException x) {
                System.err.printf("Eingabe/Ausgabe-Fehler beim laden von %s%n", p);
                return missingTexture;
            }
        });

    }

}
