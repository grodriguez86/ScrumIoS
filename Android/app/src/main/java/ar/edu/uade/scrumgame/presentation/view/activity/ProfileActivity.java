package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.view.Window;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.view.fragment.ProfileFragment;

public class ProfileActivity extends BaseActivity implements HasComponent<LevelComponent>, ProfileFragment.ProfileListener {

    private LevelComponent levelComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.activity_profile);
        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, ProfileFragment.newInstance());
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
    public void onLoggedOut() {
        this.navigator.navigateToLogin(this);
    }
}
