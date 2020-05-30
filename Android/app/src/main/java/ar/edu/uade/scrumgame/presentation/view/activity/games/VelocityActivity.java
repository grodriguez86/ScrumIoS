package ar.edu.uade.scrumgame.presentation.view.activity.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.activity.BaseActivity;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.velocity.VelocityConclusionGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.velocity.VelocityResultsGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.games.velocity.VelocityIntroGameFragment;

public class VelocityActivity extends BaseActivity implements VelocityIntroGameFragment.OnVelocityGameFinishedListener, VelocityResultsGameFragment.OnContinueToConclusionListener, VelocityConclusionGameFragment.OnVelocityConclusionFinishedListener {
    private static final int INDETERMINATE_PROGRESS_FEATURE_ID = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(INDETERMINATE_PROGRESS_FEATURE_ID);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_game_velocity);
        this.initializeFragment(savedInstanceState);
    }

    private void initializeFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Fragment fragment = VelocityIntroGameFragment.newInstance();
            this.addFragment(fragment);
        }
    }

    private void addFragment(Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onGameFinished(int expectedScore, int score) {
        Fragment fragment = VelocityResultsGameFragment.newInstance(expectedScore, score);
        this.addFragment(fragment);
    }

    @Override
    public void continueToConclusion() {
        Fragment fragment = VelocityConclusionGameFragment.newInstance();
        this.addFragment(fragment);
    }

    @Override
    public void onVelocityGameEnded() {
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
