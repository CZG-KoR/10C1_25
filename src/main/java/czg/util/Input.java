package czg.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Zentraler zugriff auf gedrückte Tasten der Tastatur und Maus
 */
public class Input implements KeyListener, MouseListener {

    /**
     * Singleton der Klasse
     */
    public static final Input INSTANCE = new Input();

    private Input() {}


    /**
     * Zustände einer Taste
     */
    public enum KeyState {
        /**
         * Taste nicht gedrückt
         */
        NOT_PRESSED,
        /**
         * Taste seit diesem Frame gedrückt
         */
        PRESSED,
        /**
         * Taste seit mehr als einem Frame gedrückt
         */
        HELD;


        /**
         * @return Ob die Taste gedrückt ist, egal wie lange schon
         */
        public boolean isDown() {
            return this == PRESSED || this == HELD;
        }
    }

    /**
     * Zustände der Tastatur-Tasten
     */
    private final Map<Integer, KeyState> keyStates = new ConcurrentHashMap<>();
    /**
     * Zustände der Maus-Tasten
     */
    private final Map<Integer, KeyState> mouseStates = new ConcurrentHashMap<>();

    /**
     * Abfrage des Zustandes einer Tastatur-Taste
     * @param keyCode Siehe {@link KeyEvent}-Klasse. Z.B. {@link KeyEvent#VK_SPACE} für Leertaste.
     * @return Zustand der Taste. Siehe {@link KeyState}.
     */
    public KeyState getKeyState(int keyCode) {
        return keyStates.getOrDefault(keyCode, KeyState.NOT_PRESSED);
    }

    /**
     * Abfrage des Zustandes einer Maus-Taste
     * @param button Siehe {@link MouseEvent}-Klasse. Z.B. {@link MouseEvent#BUTTON1} für die linke Maustaste.
     * @return Zustand der Taste. Siehe {@link KeyState}.
     */
    public KeyState getMouseState(int button) {
        return mouseStates.getOrDefault(button, KeyState.NOT_PRESSED);
    }

    /**
     * Alle Tasten, die aktuell {@link KeyState#PRESSED} sind, werden
     * von dieser Funktion stattdessen als {@link KeyState#HELD} eingetragen.
     */
    public void updatePressedToHeld() {
        // Den Code in der forEach-Funktion für jede der beiden Maps
        // ausführen, um keinen Code zu doppeln
        Stream.of(keyStates, mouseStates)
                .forEach(map -> map.keySet().stream()
                        // Die key codes herausfiltern, denen KeyState.PRESSED zugeordnet ist
                        .filter(code -> map.get(code) == KeyState.PRESSED)
                        // Die gefilterten key codes in eine Liste speichern. Sonst
                        // würde die folgende forEach-Funktion die Daten der Maps ändern,
                        // während noch über die gefilterten Schlüssel iteriert wird
                        .collect(Collectors.toList())
                        .forEach(
                                code -> map.put(code, KeyState.HELD)
                        )
                );
    }


    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keyStates.put(keyEvent.getKeyCode(), KeyState.PRESSED);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mouseStates.put(mouseEvent.getButton(), KeyState.PRESSED);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mouseStates.remove(mouseEvent.getButton());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keyStates.remove(keyEvent.getKeyCode());
    }


    // Nicht verwendet

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
