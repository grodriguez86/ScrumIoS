package ar.edu.uade.scrumgame.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.view.fragment.LevelFragment;

public class LevelActivity extends BaseActivity implements HasComponent<LevelComponent>, LevelFragment.SubLevelListListener {

    private LevelComponent levelComponent;

    private Integer levelCode;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LevelActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.activity_level);
        this.initializeInjector();
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
    public void onSubLevelClicked(String levelName, SubLevelModel subLevelModel) {
        this.navigator.navigateToInfoTheory(this, levelCode, levelName, subLevelModel.getCode());
    }
}
