package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;

public class PokerPlanningGameFragment extends GameFragment implements GameContentView {

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_poker_planning;
    }

    @Override
    protected void doLoadGame() {

    }

    @Override
    public void onCorrectAttempt() {
        this.onGameCompletedListener.onGameCompleted(gameCode);
    }

    @Override
    public void onFailedAttempt() {

    }

    @Override
    public void checkAttempt() {
        this.onCorrectAttempt();
    }
}