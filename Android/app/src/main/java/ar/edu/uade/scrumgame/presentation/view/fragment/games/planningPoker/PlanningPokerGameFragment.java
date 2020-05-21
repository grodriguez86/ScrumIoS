package ar.edu.uade.scrumgame.presentation.view.fragment.games.planningPoker;

import android.content.Intent;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.activity.games.PlanningPokerActivity;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;

import static android.app.Activity.RESULT_CANCELED;

public class PlanningPokerGameFragment extends GameFragment implements GameContentView {

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_poker_planning;
    }

    @Override
    protected void doLoadGame() {
        Intent intent = new Intent(getActivity(), PlanningPokerActivity.class);
        String EXTRA_PARAM_GAME_MODEL = "ar.edu.uade.scrumgame.INTENT_EXTRA_PARAM_GAME_MODEL";
        intent.putExtra(EXTRA_PARAM_GAME_MODEL,this.infoGameModel);
        int ON_GAME_COMPLETED_CODE = 90;
        startActivityForResult(intent, ON_GAME_COMPLETED_CODE);
    }

    @Override
    public void onCorrectAttempt() {
        super.onCorrectAttempt();
        this.onGameCompletedListener.onGameCompleted(gameCode);
    }

    @Override
    public void onFailedAttempt() {

    }

    @Override
    public void checkAttempt() {
        super.checkAttempt();
        this.onCorrectAttempt();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            this.getActivity().finish();
        } else if (this.getActivity() != null) {
            this.checkAttempt();
        }
    }
}