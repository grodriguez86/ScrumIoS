package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.view.Window;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.view.fragment.InfoTheoryFragment;

public class InfoTheoryActivity extends BaseActivity implements HasComponent<LevelComponent>, InfoTheoryFragment.PlaySubLevelListener {

    private LevelComponent levelComponent;

    private Integer levelCode;

    private String levelName;

    private String subLevelCode;

    private Integer currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.activity_info_theory);
        Bundle bundle = new Bundle();
        levelCode = getIntent().getExtras().getInt("levelCode");
        levelName = getIntent().getExtras().getString("levelName");
        subLevelCode = getIntent().getExtras().getString("subLevelCode");
        currentGame = getIntent().getExtras().getInt("currentGame");
        bundle.putInt("levelCode", levelCode);
        bundle.putString("levelName", levelName);
        bundle.putString("subLevelCode", subLevelCode);
        bundle.putInt("currentGame", currentGame);
        InfoTheoryFragment infoTheoryFragment = new InfoTheoryFragment();
        infoTheoryFragment.setArguments(bundle);
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, infoTheoryFragment);
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
    public void onPlayClicked(Integer levelCode, String levelTitle, String subLevelCode,
                              String subLevelTitle) {
        this.navigator.navigateToPlaySubLevel(this, levelCode, levelTitle,
                subLevelCode, subLevelTitle);
    }
}
