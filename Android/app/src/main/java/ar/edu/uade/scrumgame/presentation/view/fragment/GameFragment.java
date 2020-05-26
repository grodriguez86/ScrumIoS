package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.presenter.GamePresenter;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.activity.InfoGameActivity;
import butterknife.ButterKnife;

import javax.inject.Inject;

public abstract class GameFragment extends BaseFragment implements GameContentView {
    protected static final String BUNDLE_EXTRA_PARAM_INFO_GAME = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_INFO_GAME";
    protected String gameCode;
    protected InfoGameModel infoGameModel;
    private Boolean firstAttempt = true;
    @Inject
    GamePresenter gamePresenter;
    private Integer secondsSpent = 0;
    private Handler handler = new Handler();
    private Runnable counterRunnable = new Runnable() {
        @Override
        public void run() {
            secondsSpent += 1;
            handler.postDelayed(counterRunnable, 1000);
        }
    };

    public interface OnGameCompletedListener {
        void onGameCompleted(String gameCode);
    }

    protected OnGameCompletedListener onGameCompletedListener;

    protected abstract Integer getFragmentId();

    protected abstract void doLoadGame();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gamePresenter.initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(getFragmentId(), container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            this.loadGame();
        }
    }

    private void loadGame() {
        this.infoGameModel = getArguments().getParcelable(BUNDLE_EXTRA_PARAM_INFO_GAME);
        if (infoGameModel != null) {
            this.gameCode = infoGameModel.getCode();
            this.doLoadGame();
            this.handler.post(counterRunnable);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        this.getComponent(LevelComponent.class).inject(this);
        super.onAttach(activity);
        if (activity instanceof InfoGameActivity) {
            this.onGameCompletedListener = (OnGameCompletedListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onGameCompletedListener = null;
    }

    @Override
    public void onCorrectAttempt() {
        if (this.firstAttempt) {
            this.gamePresenter.logEventGameCorrectAnswerFirstTry(this.infoGameModel, String.valueOf(secondsSpent));
        } else {
            this.gamePresenter.logEventGameCorrectAnswer(this.infoGameModel, String.valueOf(secondsSpent));
        }
    }

    @Override
    public void onFailedAttempt() {
        this.firstAttempt = false;
        this.gamePresenter.logEventGameWrongAnswer(this.infoGameModel, String.valueOf(secondsSpent));
        this.handler.post(counterRunnable);
    }

    @Override
    public void checkAttempt() {
        this.handler.removeCallbacks(counterRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacks(counterRunnable);
    }
}
