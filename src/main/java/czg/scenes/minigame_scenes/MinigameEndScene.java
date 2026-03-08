package czg.scenes.minigame_scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.BaseObject;
import czg.objects.ButtonObject;
import czg.objects.ItemObject;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.util.Images;

public class MinigameEndScene extends BaseScene {
    public MinigameEndScene(boolean won, int level, ItemObject reward) {
        objects.add(new BackdropObject(Images.get("/assets/minigames/general/background_overlay.png")));

        if(won) {
            BaseObject banner = new BaseObject(Images.get("/assets/minigames/general/banner_won.png"));
            banner.x = (MainWindow.WIDTH - banner.width) / 2;
            banner.y = (MainWindow.HEIGHT - banner.height) / 3;

            objects.add(banner);

            BaseObject rewardContainer = new BaseObject(Images.get("/assets/minigames/general/reward_container.png"));
            rewardContainer.x = (MainWindow.WIDTH - rewardContainer.width) / 2;
            rewardContainer.y = (int)((MainWindow.HEIGHT - rewardContainer.height) * 0.9);

            objects.add(rewardContainer);

            BaseObject rewardItem = new BaseObject(reward.sprite);
            rewardItem.x = (MainWindow.WIDTH - rewardContainer.width + rewardContainer.width-rewardItem.width) / 2;
            rewardItem.y = (int)((MainWindow.HEIGHT - rewardContainer.height) * 0.9) + (rewardContainer.height-rewardItem.height) / 2;

            objects.add(rewardItem);
        } else {
            BaseObject banner = new BaseObject(Images.get("/assets/minigames/general/banner_lost.png"));
            banner.x = (MainWindow.WIDTH - banner.width) / 2;
            banner.y = (MainWindow.HEIGHT - banner.height) / 3;

            objects.add(banner);
        }

        ButtonObject retryButton = new ButtonObject(Images.get("/assets/minigames/general/button_retry.png"), () -> {
            SceneStack.INSTANCE.pop();
            SceneStack.INSTANCE.pop();
            SceneStack.INSTANCE.pop();

            InformaticsLevelScene.resetAndStartMinigame(level);
        });

        retryButton.x = (int)((MainWindow.WIDTH - retryButton.width) * 0.4);
        retryButton.y = (int)((MainWindow.HEIGHT - retryButton.height) * 0.6);

        objects.add(retryButton);

        ButtonObject menuButton = new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"), () -> {
            SceneStack.INSTANCE.pop();
            SceneStack.INSTANCE.pop();
            SceneStack.INSTANCE.pop();

            InformaticsLevelScene.resetAndStartMinigame(-1);
        });

        menuButton.x = (int)((MainWindow.WIDTH - menuButton.width) * 0.5);
        menuButton.y = (int)((MainWindow.HEIGHT - menuButton.height) * 0.6);

        objects.add(menuButton);

        ButtonObject exitButton = new ButtonObject(Images.get("/assets/minigames/general/button_exit.png"), () -> {
            SceneStack.INSTANCE.pop();
            SceneStack.INSTANCE.pop();
            SceneStack.INSTANCE.pop();

            InformaticsLevelScene.resetMinigame();
        });

        exitButton.x = (int)((MainWindow.WIDTH - exitButton.width) * 0.6);
        exitButton.y = (int)((MainWindow.HEIGHT - exitButton.height) * 0.6);

        objects.add(exitButton);
    }
}
