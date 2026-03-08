package czg.scenes.minigame_scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.BaseObject;
import czg.objects.ButtonObject;
import czg.objects.ItemObject;
import czg.objects.minigame_objects.InfoPuzzleObject;
import czg.objects.minigame_objects.MinigameNameObject;
import czg.scenes.InfogangScene;
import czg.scenes.SceneStack;
import czg.util.Images;

public class InformaticsLevelScene extends LevelScene {
    public InformaticsLevelScene(int level) {
        super(MinigameNameObject.INFORMATICS, level);

        objects.add(new BackdropObject(Images.get("/assets/minigames/informatics/background.png")));

        ButtonObject exitButton = new ButtonObject(Images.get("/assets/minigames/general/button_exit.png"), () -> {SceneStack.INSTANCE.pop(); SceneStack.INSTANCE.pop();}
        );
        exitButton.width /= 2;
        exitButton.height /= 2;
        exitButton.x = (int) (MainWindow.WIDTH - exitButton.width * 2);
        exitButton.y = (int) (exitButton.height * 0.3);

        objects.add(exitButton);

        ButtonObject menuButton = new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"), SceneStack.INSTANCE::pop);
        menuButton.width /= 2;
        menuButton.height /= 2;
        menuButton.x = (int) (MainWindow.WIDTH - menuButton.width * 4);
        menuButton.y = (int) (menuButton.height * 0.3);

        objects.add(menuButton);

        InfoPuzzleObject puzzle = InfoPuzzleObject.getPuzzle(level);

        int availableHeight = (int) (MainWindow.HEIGHT * 0.7);
        int gateHeight = availableHeight / puzzle.answers.length;

        for (int i = 0; i < puzzle.answers.length; i++) {
            int finalI = i;
            objects.add(new ButtonObject(
                    puzzle.answers[i].sprite,
                    (int) (MainWindow.WIDTH * 0.125),
                    (MainWindow.HEIGHT - availableHeight) / 2 + i * gateHeight,
                    () -> {
                        if (puzzle.answers[finalI] == puzzle.solution)
                            minigameWon();
                        else
                            minigameLost();
                    })
            );
        }

        objects.add(new BaseObject(
                puzzle.sprite,
                (int) (MainWindow.WIDTH * 0.3),
                (MainWindow.HEIGHT - availableHeight) / 2,
                (int) (MainWindow.WIDTH * 0.6),
                availableHeight
        ));
    }

    public static MinigameScene generateInformaticsMinigame() {
        return new MinigameScene(
                new InformaticsLevelScene(0),
                new InformaticsLevelScene(0),
                new InformaticsLevelScene(0)
        );
    }

    public static void resetMinigame() {
        MinigameScene informaticsMinigame = InformaticsLevelScene.generateInformaticsMinigame();

        InfogangScene infogangScene = SceneStack.INSTANCE.getTop() instanceof InfogangScene ? (InfogangScene) SceneStack.INSTANCE.getTop() : null;

        if (infogangScene.objects.getLast() instanceof ButtonObject) {
            ((ButtonObject) infogangScene.objects.getLast()).method = informaticsMinigame::startMinigame;
        }
    }

    public static void resetAndStartMinigame(int level) {
        MinigameScene informaticsMinigame = InformaticsLevelScene.generateInformaticsMinigame();

        InfogangScene infogangScene = SceneStack.INSTANCE.getTop() instanceof InfogangScene ? (InfogangScene) SceneStack.INSTANCE.getTop() : null;

        if (infogangScene.objects.getLast() instanceof ButtonObject) {
            ((ButtonObject) infogangScene.objects.getLast()).method = informaticsMinigame::startMinigame;
            ((ButtonObject) infogangScene.objects.getLast()).method.run();

            LevelSelectorScene levelSelectorScene = SceneStack.INSTANCE.getTop() instanceof LevelSelectorScene ? (LevelSelectorScene) SceneStack.INSTANCE.getTop() : null;

            if (levelSelectorScene.objects.getLast() instanceof ButtonObject &&
                levelSelectorScene.objects.get(levelSelectorScene.objects.size()-2) instanceof ButtonObject &&
                levelSelectorScene.objects.get(levelSelectorScene.objects.size()-3) instanceof ButtonObject
            ) {
                switch (level) {
                    case 0 ->
                            ((ButtonObject) levelSelectorScene.objects.get(levelSelectorScene.objects.size() - 3)).method.run();
                    case 1 ->
                            ((ButtonObject) levelSelectorScene.objects.get(levelSelectorScene.objects.size() - 2)).method.run();
                    case 2 ->
                            ((ButtonObject) levelSelectorScene.objects.getLast()).method.run();
                }
            }
        }
    }
}
