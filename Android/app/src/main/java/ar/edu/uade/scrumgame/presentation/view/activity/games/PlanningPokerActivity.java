package ar.edu.uade.scrumgame.presentation.view.activity.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.view.activity.BaseActivity;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.planningPoker.PlanningPokerIntroGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.PlanningPokerGameEndedListener;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.PlanningPokerNextStepListener;

public class PlanningPokerActivity extends BaseActivity implements PlanningPokerNextStepListener, PlanningPokerGameEndedListener {
    private static final int INDETERMINATE_PROGRESS_FEATURE_ID = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(INDETERMINATE_PROGRESS_FEATURE_ID);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_game_planning_poker);
        this.initializeFragment(savedInstanceState);
    }

    private void initializeFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            String EXTRA_PARAM_GAME_MODEL = "ar.edu.uade.scrumgame.INTENT_EXTRA_PARAM_GAME_MODEL";
            InfoGameModel infoGameModel = getIntent().getParcelableExtra(EXTRA_PARAM_GAME_MODEL);
            Fragment fragment = PlanningPokerIntroGameFragment.newInstance(infoGameModel);
            this.replaceFragment(fragment);
        }
    }

    protected void replaceFragment(Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void goToNextStep(Fragment fragment) {
        if (fragment != null) {
            this.replaceFragment(fragment);
        }
    }

    @Override
    public void finishGame() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        this.showAlert(getString(R.string.confirmation),
                getString(R.string.exit_game),
                this,
                getString(R.string.quit),
                (dialog, which) -> {
                    super.onBackPressed();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_CANCELED, returnIntent);
                    finish();
                });
    }
}
