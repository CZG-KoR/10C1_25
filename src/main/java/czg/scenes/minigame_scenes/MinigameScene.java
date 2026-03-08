/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes.minigame_scenes;

import czg.objects.ButtonObject;
import czg.scenes.BaseScene;
import czg.scenes.InfogangScene;
import czg.scenes.SceneStack;

/**
 * Ein Minigame Template bestehend aus einer {@code LevelSelectorScene}, welche die einzelnen Level verknüpft
 * und jeweils drei Leveln.
 */
public class MinigameScene extends BaseScene {
    final LevelSelectorScene levelSelector;
    final BaseScene level1;
    final BaseScene level2;
    final BaseScene level3;

    /**
     * Einen neues Minigame erstellen.
     * @param level1 Level 1
     * @param level2 Level 2
     * @param level3 Level 3
     */
    public MinigameScene(BaseScene level1, BaseScene level2, BaseScene level3) {
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;

        this.levelSelector = new LevelSelectorScene(level1, level2, level3);
    }

    /**
     * Startet das Minigame
     */
    public void startMinigame() {
        SceneStack.INSTANCE.push(levelSelector);
    }
}
