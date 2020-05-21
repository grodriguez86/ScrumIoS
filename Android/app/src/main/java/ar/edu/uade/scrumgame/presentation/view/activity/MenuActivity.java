package ar.edu.uade.scrumgame.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import ar.edu.uade.scrumgame.presentation.models.UserModel;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.view.fragment.MenuFragment;

public class MenuActivity extends BaseActivity implements HasComponent<LevelComponent>, MenuFragment.LevelListListener {

    private LevelComponent levelComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MenuActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.activity_menu);
        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new MenuFragment());
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
    public void onLevelClicked(LevelModel levelModel) {
        this.navigator.navigateToLevel(this, levelModel.getCode());
    }

    @Override
    public void navigateToProfile(UserModel loggedInUser) {
        this.navigator.navigateToProfile(this);
    }

    @Override
    public void onBackPressed() {
        this.showAlert(getString(R.string.confirmation),
                getString(R.string.quit_application_confirmation),
                this,
                getString(R.string.quit),
                (dialog, which) -> {
                    MenuActivity.super.onBackPressed();
                    finishAffinity();
                });
    }
}
