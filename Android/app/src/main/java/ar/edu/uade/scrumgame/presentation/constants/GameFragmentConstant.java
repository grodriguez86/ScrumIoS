package ar.edu.uade.scrumgame.presentation.constants;

import ar.edu.uade.scrumgame.presentation.view.fragment.level.Level1SubLevel1Game1Fragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.level.Level1SubLevel1Game2Fragment;

public enum GameFragmentConstant {
    LEVEL_1_SUB_LEVEL_1_GAME_1("1.1.1", Level1SubLevel1Game1Fragment.class.getName()),
    LEVEL_1_SUB_LEVEL_1_GAME_2("1.1.2", Level1SubLevel1Game2Fragment.class.getName());

    private String gameCode;
    private String fragmentClassName;

    GameFragmentConstant(String gameCode, String fragmentClassName) {
        this.gameCode = gameCode;
        this.fragmentClassName = fragmentClassName;
    }

    public static String getFragmentNameForGame(String gameCode) {
        GameFragmentConstant[] games = GameFragmentConstant.values();
        for (GameFragmentConstant game : games) {
            if (game.gameCode.equals(gameCode)) {
                return game.fragmentClassName;
            }
        }
        throw new IllegalArgumentException("Game not implemented");
    }
}
