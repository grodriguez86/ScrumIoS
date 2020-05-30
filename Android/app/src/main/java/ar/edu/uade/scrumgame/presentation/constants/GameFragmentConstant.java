package ar.edu.uade.scrumgame.presentation.constants;

import ar.edu.uade.scrumgame.presentation.view.fragment.games.SelectionGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.ImageQuizGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.planningPoker.PlanningPokerGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.ShortTextQuizGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.TextQuizGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.ScrumFlowDraggableGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.TextChoicesGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.TrueFalseGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.velocity.VelocityGameFragment;

public enum GameFragmentConstant {
    TEXT_QUIZ("textQuiz", TextQuizGameFragment.class.getName()),
    SCRUM_FLOW_DRAGGABLE("scrumFlowDraggable", ScrumFlowDraggableGameFragment.class.getName()),
    TEXT_CHOICES_QUIZ("textChoiceQuiz", TextChoicesGameFragment.class.getName()),
    TRUE_FALSE_QUIZ("trueFalseQuiz", TrueFalseGameFragment.class.getName()),
    IMAGE_QUIZ("imageQuiz", ImageQuizGameFragment.class.getName()),
    VELOCITY("velocity", VelocityGameFragment.class.getName()),
    POKER_PLANNING("pokerPlanning", PlanningPokerGameFragment.class.getName()),
    SELECTION("selection", SelectionGameFragment.class.getName()),
    SHORT_TEXT_QUIZ("shortTextQuiz", ShortTextQuizGameFragment.class.getName());

    private String gameType;
    private String fragmentClassName;

    GameFragmentConstant(String gameType, String fragmentClassName) {
        this.gameType = gameType;
        this.fragmentClassName = fragmentClassName;
    }

    public static String getFragmentNameForType(String gameType) {
        GameFragmentConstant[] games = GameFragmentConstant.values();
        for (GameFragmentConstant game : games) {
            if (game.gameType.equals(gameType)) {
                return game.fragmentClassName;
            }
        }
        throw new IllegalArgumentException(String.format("Game not implemented: %s", gameType));
    }
}
