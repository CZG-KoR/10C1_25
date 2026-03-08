package czg.objects.minigame_objects;

import czg.util.Images;

import java.awt.Image;
import java.util.Random;

public enum InfoPuzzleObject {
    P_00("/assets/minigames/informatics/puzzle_1_1.png", LogicGateObject.AND, 4),
    P_01("/assets/minigames/informatics/puzzle_1_2.png", LogicGateObject.OR, 4),
    P_02("/assets/minigames/informatics/puzzle_1_3.png", LogicGateObject.NAND, 4);

    public static final InfoPuzzleObject[][] PUZZLES = {
        {
            InfoPuzzleObject.P_00,
            InfoPuzzleObject.P_01,
            InfoPuzzleObject.P_02
        }
    };

    public final Image sprite;
    public final LogicGateObject solution;
    public final LogicGateObject[] answers;

    InfoPuzzleObject(String path, LogicGateObject solution, int amountOfAnswers) {
        this.sprite = Images.get(path);
        this.solution = solution;
        this.answers = LogicGateObject.getRandomArray(amountOfAnswers, solution);

        int r = (int) (new Random().nextDouble() * amountOfAnswers);

        this.answers[r] = solution;
    }

    public static InfoPuzzleObject getPuzzle(int level) {
        int r = (int) (new Random().nextDouble() * 3);

        return PUZZLES[level][r];
    }
}
