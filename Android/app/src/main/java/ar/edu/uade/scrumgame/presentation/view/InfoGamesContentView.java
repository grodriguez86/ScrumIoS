package ar.edu.uade.scrumgame.presentation.view;

import java.util.List;

import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;

public interface InfoGamesContentView extends LoadDataView {
    void loadGames(List<InfoGameModel> infoGameModelCollection);

    void playNextLevel();

    void onCompleteGame(String gameCode);

    void goToSublevelMenu();
}
