package czg.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Input implements KeyListener, MouseListener {

    public static final Input INSTANCE = new Input();


    public enum KeyState { NOT_PRESSED, PRESSED, HELD }

    private final Map<Integer, KeyState> keyStates = new ConcurrentHashMap<>();
    private final Map<Integer, KeyState> mouseStates = new ConcurrentHashMap<>();

    /**
     *
     * @param keyCode Siehe {@link KeyEvent}-Klasse. Z.B. {@link KeyEvent#VK_SPACE} für Leertaste.
     * @return Zustand der Taste. Siehe {@link KeyState}.
     */
    public KeyState getKeyState(int keyCode) {
        return keyStates.getOrDefault(keyCode, KeyState.NOT_PRESSED);
    }

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
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keyStates.put(keyEvent.getKeyCode(), KeyState.PRESSED);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keyStates.remove(keyEvent.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

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
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
