package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.view.activity.InfoGameActivity;
import butterknife.ButterKnife;

public abstract class GameFragment extends BaseFragment {
    protected static final String BUNDLE_EXTRA_PARAM_INFO_GAME = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_INFO_GAME";
    protected String gameCode;
    protected InfoGameModel infoGameModel;

    public interface OnGameCompletedListener {
        void onGameCompleted(String gameCode);
    }

    protected OnGameCompletedListener onGameCompletedListener;

    public GameFragment() {
        this.setRetainInstance(true);
    }


    protected abstract Integer getFragmentId();

    protected abstract void doLoadGame();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(LevelComponent.class).inject(this);
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
        }
    }


    @Override
    public void onAttach(Activity activity) {
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

}
