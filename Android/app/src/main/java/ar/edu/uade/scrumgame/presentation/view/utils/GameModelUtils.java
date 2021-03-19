package ar.edu.uade.scrumgame.presentation.view.utils;

import ar.edu.uade.scrumgame.presentation.models.GameContentModel;

import java.util.ArrayList;
import java.util.List;

public class GameModelUtils {

    public static Integer getCorrectOptionIndex(List<GameContentModel> options) {
        int correctOptionIndex = -1;
        if (options != null) {
            for (int index = 0; index < options.size(); index++) {
                if (options.get(index).getCorrect()) {
                    correctOptionIndex = index;
                }
            }
        }
        return correctOptionIndex;
    }

    public static List<Integer> getCorrectOptionIndexes(List<GameContentModel> options) {
        List<Integer> correctOptionIndexes = new ArrayList<>();
        if (options != null) {
            for (int index = 0; index < options.size(); index++) {
                if (options.get(index).getCorrect()) {
                    correctOptionIndexes.add(index);
                }
            }
        }
        return correctOptionIndexes;
    }
}
