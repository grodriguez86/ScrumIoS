package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.view.Window;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.view.fragment.SignupFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.SignUpStep1Listener;

public class SignupActivity extends BaseActivity implements HasComponent<LevelComponent>, SignUpStep1Listener {
    private LevelComponent levelComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_signup);
        SignupFragment signupFragment = new SignupFragment();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, signupFragment);
        }
    }

    @Override
    public void onSignupClicked(String email, String password) {
        this.navigator.navigateToSignupDetails(this);
    }

    private void initializeInjector() {
        this.levelComponent = DaggerLevelComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public LevelComponent getComponent() {
        return levelComponent;
    }

    @Override
    public void onSignupDetailsCompleted() {
        this.navigator.navigateToMenu(this);
    }

    @Override
    public void onSignupDetailsFailed() {
        this.navigator.navigateToLogin(this);
    }
}
