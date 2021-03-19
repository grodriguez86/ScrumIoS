package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.view.Window;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.view.fragment.LevelFragment;

public class LevelActivity extends BaseActivity implements HasComponent<LevelComponent>, LevelFragment.SubLevelListListener {

    private LevelComponent levelComponent;

    private Integer levelCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.activity_level);
        Bundle bundle = new Bundle();
        levelCode = (Integer) getIntent().getExtras().get("levelCode");
        bundle.putInt("levelCode", levelCode);
        LevelFragment levelFragment = new LevelFragment();
        levelFragment.setArguments(bundle);
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, levelFragment);
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
    public void onSubLevelClicked(String levelName, SubLevelModel subLevelModel, ProgressModel progressModel) {
        if (progressModel.isTutorialCompleted()) {
            this.navigator.navigateToPlaySubLevel(this, levelCode, levelName,
                    subLevelModel.getCode(), subLevelModel.getName());
        } else {
            this.navigator.navigateToInfoTheory(this, levelCode, levelName,
                    subLevelModel.getCode(), progressModel.getActualGame());
        }
    }
}
