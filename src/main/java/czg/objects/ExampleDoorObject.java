package czg.objects;

import czg.MainWindow;
import czg.scenes.BaseScene;
import czg.util.Images;

import java.util.function.Supplier;

import static czg.MainWindow.PIXEL_SCALE;

public class ExampleDoorObject extends BaseObject {

    private final Supplier<BaseScene> destination;

    public ExampleDoorObject(int x, int y, Supplier<BaseScene> destination) {
        super(Images.get("/assets/background/door.png"), x, y, PIXEL_SCALE * 12, PIXEL_SCALE * 24);
        this.destination = destination;
    }

    @Override
    public void update(BaseScene scene) {
        if(scene.getOverlappingObjects(this).contains(ExamplePlayerObject.INSTANCE)) {
            BaseScene newScene = destination.get();

            // Spielfigur versetzen
            scene.objects.remove(ExamplePlayerObject.INSTANCE);
            newScene.objects.add(ExamplePlayerObject.INSTANCE);

            // Spielfigur hat die Tür berührt: neue Szene Laden
            MainWindow.getInstance().SCENE_STACK.replace(scene, newScene);
        }
    }

}
