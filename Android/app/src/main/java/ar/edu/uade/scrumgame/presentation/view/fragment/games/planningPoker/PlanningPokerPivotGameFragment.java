package ar.edu.uade.scrumgame.presentation.view.fragment.games.planningPoker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.PlanningPokerNextStepListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanningPokerPivotGameFragment extends Fragment {
    @BindView(R.id.continue_button)
    Button continueButton;
    private PlanningPokerNextStepListener planningPokerNextStepListener;


    public static PlanningPokerPivotGameFragment newInstance() {
        return new PlanningPokerPivotGameFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PlanningPokerNextStepListener) {
            this.planningPokerNextStepListener = (PlanningPokerNextStepListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.planningPokerNextStepListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_planning_poker_pivot, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.continue_button)
    public void continueToNextStep() {
        if (this.planningPokerNextStepListener != null) {
            Fragment userStoryFragment = PlanningPokerUserStoryGameFragment.newInstance();
            this.planningPokerNextStepListener.goToNextStep(userStoryFragment);
        }
    }
}