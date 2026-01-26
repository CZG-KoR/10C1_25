package czg.scenes;

import org.junit.Test;

import static org.junit.Assert.*;

public class SceneStackTest {

    @Test
    public void testStack() {
        final int[] x = {0};
        SceneStack stack = new SceneStack();

        BaseScene bottom = new BaseScene();

        BaseScene middle = new BaseScene() {
            @Override
            public void update() {
                super.update();
                x[0]++;
            }
        };
        middle.coverPausesLogic = true;

        BaseScene top = new BaseScene();

        // Nur zwei Szenen
        stack.push(bottom);
        stack.push(middle);

        // Nur die untere Szene sollte als bedeckt markiert sein
        assertTrue(bottom.isCovered);
        assertFalse(middle.isCovered);

        // Die mittlere Szene ist nicht verdeckt, sollte also auch ihren Code ausgeführt haben
        stack.update();
        assertEquals(1, x[0]);

        // Dritte Szene hinzufügen
        stack.push(top);

        // Nun sollten alle Szenen außer die oberste verdeckt sein
        assertTrue(bottom.isCovered);
        assertTrue(middle.isCovered);
        assertFalse(top.isCovered);

        // Die mittlere Szene ist jetzt verdeckt, sollte also ihren Code nicht ausgeführt haben
        stack.update();
        assertEquals(1, x[0]);

        // Oberste Szene wieder entfernen
        stack.pop();

        // Die mittlere Szene sollte jetzt nicht mehr als bedeckt markiert sein
        assertTrue(bottom.isCovered);
        assertFalse(middle.isCovered);
    }

}
