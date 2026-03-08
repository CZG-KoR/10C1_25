package czg.scenes.minigame_scenes;

import czg.MainWindow;
import czg.objects.BaseObject;
import czg.objects.ItemObject;
import czg.objects.PlayerObject;
import czg.objects.minigame_objects.MinigameNameObject;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.scenes.cover_settings.CoverSettings;

public abstract class LevelScene extends BaseScene {
    final int LEVEL;
    final ItemObject REWARD;

    public LevelScene(MinigameNameObject minigameType, int level) {
        super(new CoverSettings(false, true, false));
        this.LEVEL = level;
        this.REWARD = ItemObject.getMinigameReward(minigameType, level);
    }

    protected void minigameWon() {
        SceneStack.INSTANCE.push(new MinigameEndScene(true, LEVEL, REWARD));

        PlayerObject.INSTANCE.inventar.add(REWARD);
    }

    protected void minigameLost() {
        SceneStack.INSTANCE.push(new MinigameEndScene(false, LEVEL, REWARD));
    }
}
