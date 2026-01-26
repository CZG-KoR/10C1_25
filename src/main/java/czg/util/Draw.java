package czg.util;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Hilfsfunktionen für Grafik
 */
public final class Draw {

    /**
     * Schriftart für Überschriften
     */
    public static final Font FONT_TITLE = new Font("Arial", Font.BOLD, 32);

    /**
     * Zeichnet Text, sodass dessen Mittelpunkt bei den angegebenen Koordinaten liegt. Farbe und Schrift
     * sollten vorher schon eingestellt sein.
     * @param g Graphics-Objekt. Sollte durch den entsprechenden Parameter einer draw()-Methode bereitgestellt werden.
     * @param text Der Text
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public static void drawTextCentered(Graphics2D g, String text, int x, int y) {
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
        g.drawString(text, (int) (x - bounds.getWidth()/2), (int) (y - bounds.getHeight()/2));
    }

}
