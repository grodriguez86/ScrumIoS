package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;

import ar.edu.uade.scrumgame.presentation.view.activity.InfoGameActivity;

public class GameFragment extends BaseFragment {

    public interface OnGameCompletedListener {
        void onGameCompleted(String gameCode);
    }

    protected OnGameCompletedListener onGameCompletedListener;

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
