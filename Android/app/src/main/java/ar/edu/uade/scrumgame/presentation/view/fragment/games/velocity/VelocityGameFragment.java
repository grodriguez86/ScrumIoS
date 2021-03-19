package ar.edu.uade.scrumgame.presentation.view.fragment.games.velocity;

import android.content.Intent;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.activity.games.VelocityActivity;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;

import static android.app.Activity.RESULT_CANCELED;

public class VelocityGameFragment extends GameFragment implements GameContentView {
    private static int ON_GAME_COMPLETED_CODE = 90;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_velocity;
    }

    @Override
    protected void doLoadGame() {
        Intent intent = new Intent(getActivity(), VelocityActivity.class);
        startActivityForResult(intent, ON_GAME_COMPLETED_CODE);
    }

    @Override
    protected void doLoadCompletedGame() {

    }

    @Override
    public void onCorrectAttempt() {
        if (!this.completed) {
            super.onCorrectAttempt();
        }
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
        if (requestCode == ON_GAME_COMPLETED_CODE) {
            if (resultCode == RESULT_CANCELED) {
                this.getActivity().finish();
            } else if (this.getActivity() != null) {
                this.checkAttempt();
            }
        }
    }
}
