package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.view.Window;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.view.fragment.LoginFragment;

public class LoginActivity extends BaseActivity implements HasComponent<LevelComponent>, LoginFragment.LoginListener {

    private LevelComponent levelComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new LoginFragment());
        }
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
    public void onLogin() {
        this.navigator.navigateToMenu(this);
    }

    @Override
    public void onSignupPressed() {
        this.navigator.navigateToSignup(this);
    }

    @Override
    public void onGoToSignupDetails() {
        this.navigator.navigateToSignupDetails(this);
    }
}
