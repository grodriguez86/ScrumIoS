package ar.edu.uade.scrumgame.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.view.fragment.InfoTheoryFragment;

public class InfoTheoryActivity extends BaseActivity implements HasComponent<LevelComponent> {

    private LevelComponent levelComponent;

    private Integer levelCode;

    private String levelName;

    private String subLevelCode;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, InfoTheoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.activity_info_theory);
        this.initializeInjector();
        Bundle bundle = new Bundle();
        levelName = (String) getIntent().getExtras().get("levelName");
        subLevelCode = (String) getIntent().getExtras().get("subLevelCode");
        bundle.putString("levelName", levelName);
        bundle.putString("subLevelCode", subLevelCode);
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

}
