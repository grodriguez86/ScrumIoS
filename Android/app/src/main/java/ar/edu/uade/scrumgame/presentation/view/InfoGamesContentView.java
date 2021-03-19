package ar.edu.uade.scrumgame.presentation.view;

import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;

public interface InfoGamesContentView extends LoadDataView {
    void playGame(InfoGameModel game, Float progress);

    void onCompleteGame(String gameCode);

    void goToSublevelMenu();

    void showLevelCompletedView();
}
