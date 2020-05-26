package ar.edu.uade.scrumgame.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.view.fragment.SignupDetailsFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.SignUpListener;

public class SignupDetailsActivity extends BaseActivity implements HasComponent<LevelComponent>, SignUpListener {

    private LevelComponent levelComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SignupActivity.class);
    }

    public SignupDetailsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_signup_details);
        SignupDetailsFragment signupDetailsFragment = new SignupDetailsFragment();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, signupDetailsFragment);
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
    public void onSignupDetailsCompleted() {
        this.navigator.navigateToMenu(this);
    }

    @Override
    public void onSignupDetailsFailed() {
        this.navigator.navigateToLogin(this);
    }
}
