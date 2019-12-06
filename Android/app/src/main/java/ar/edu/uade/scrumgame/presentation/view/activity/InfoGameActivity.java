package ar.edu.uade.scrumgame.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.InfoGameFragment;

public class InfoGameActivity extends BaseActivity implements HasComponent<LevelComponent>,
        GameFragment.OnGameCompletedListener, InfoGameFragment.OnGamesCompletedListener {
    private static final String INTENT_EXTRA_PARAM_LEVEL_CODE = "ar.edu.uade.scrumgame.INTENT_EXTRA_PARAM_LEVEL_CODE";
    private static final String INTENT_EXTRA_PARAM_SUB_LEVEL_CODE = "ar.edu.uade.scrumgame.INTENT_EXTRA_PARAM_SUB_LEVEL_CODE";
    private static final String INTENT_EXTRA_PARAM_LEVEL_TITLE = "ar.edu.uade.scrumgame.INTENT_EXTRA_PARAM_LEVEL_TITLE";
    private static final String INTENT_EXTRA_PARAM_SUB_LEVEL_TITLE = "ar.edu.uade.scrumgame.INTENT_EXTRA_PARAM_SUB_LEVEL_TITLE";
    private static final String INTENT_EXTRA_PARAM_CURRENT_GAME = "ar.edu.uade.scrumgame.INTENT_EXTRA_PARAM_CURRENT_GAME";
    private static final String INSTANCE_STATE_PARAM_LEVEL_CODE = "ar.edu.uade.scrumgame.INSTANCE_STATE_PARAM_LEVEL_CODE";
    private static final String INSTANCE_STATE_PARAM_SUB_LEVEL_CODE = "ar.edu.uade.scrumgame.INSTANCE_STATE_PARAM_SUB_LEVEL_TITLE";
    private static final String INSTANCE_STATE_PARAM_LEVEL_TITLE = "ar.edu.uade.scrumgame.INSTANCE_STATE_PARAM_LEVEL_TITLE";
    private static final String INSTANCE_STATE_PARAM_SUB_LEVEL_TITLE = "ar.edu.uade.scrumgame.INSTANCE_STATE_PARAM_SUB_LEVEL_CODE";
    private static final String INSTANCE_STATE_PARAM_CURRENT_GAME = "ar.edu.uade.scrumgame.INSTANCE_STATE_PARAM_CURRENT_GAME";
    private Integer levelCode;
    private String subLevelCode;
    private Integer currentGame;
    private String levelTitle;
    private String subLevelTitle;
    private LevelComponent levelComponent;
    private InfoGameFragment infoGameFragment;

    public static Intent getCallingIntent(Context context, Integer levelCode, String levelTitle, String subLevelCode, String subLevelTitle) {
        Intent callingIntent = new Intent(context, InfoGameActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_LEVEL_CODE, levelCode);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_SUB_LEVEL_CODE, subLevelCode);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_LEVEL_TITLE, levelTitle);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_SUB_LEVEL_TITLE, subLevelTitle);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CURRENT_GAME, 0);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.activity_info_game);
        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.levelCode = getIntent().getIntExtra(INTENT_EXTRA_PARAM_LEVEL_CODE, 1);
            this.subLevelCode = getIntent().getStringExtra(INTENT_EXTRA_PARAM_SUB_LEVEL_CODE);
            this.levelTitle = getIntent().getStringExtra(INTENT_EXTRA_PARAM_LEVEL_TITLE);
            this.subLevelTitle = getIntent().getStringExtra(INTENT_EXTRA_PARAM_SUB_LEVEL_TITLE);
            this.currentGame = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CURRENT_GAME, 0);
        } else {
            this.levelCode = savedInstanceState.getInt(INSTANCE_STATE_PARAM_LEVEL_CODE);
            this.subLevelCode = savedInstanceState.getString(INSTANCE_STATE_PARAM_SUB_LEVEL_CODE);
            this.levelTitle = savedInstanceState.getString(INSTANCE_STATE_PARAM_LEVEL_TITLE);
            this.subLevelTitle = savedInstanceState.getString(INSTANCE_STATE_PARAM_SUB_LEVEL_TITLE);
            this.currentGame = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CURRENT_GAME);
        }
        this.initializeFragment(savedInstanceState);
    }

    private void initializeFragment(Bundle savedInstanceState) {
        this.infoGameFragment = InfoGameFragment.newInstance(this.levelTitle, this.subLevelCode, this.subLevelTitle, this.currentGame);
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, infoGameFragment);
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(INSTANCE_STATE_PARAM_LEVEL_CODE, this.levelCode);
        outState.putString(INSTANCE_STATE_PARAM_SUB_LEVEL_CODE, this.subLevelCode);
        outState.putInt(INSTANCE_STATE_PARAM_CURRENT_GAME, this.currentGame);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onGameCompleted(String gameCode) {
        if (infoGameFragment != null) {
            this.infoGameFragment.onCompleteGame(gameCode);
        }
    }

    @Override
    public void onGamesCompleted() {
        this.navigator.navigateToLevelAfterGameFinished(this, this.levelCode);
    }
}