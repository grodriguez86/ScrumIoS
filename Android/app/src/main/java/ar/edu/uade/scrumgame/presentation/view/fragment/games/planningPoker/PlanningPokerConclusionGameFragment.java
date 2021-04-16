package ar.edu.uade.scrumgame.presentation.view.fragment.games.planningPoker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.PlanningPokerGameEndedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanningPokerConclusionGameFragment extends Fragment {
    private static final String BUNDLE_EXTRA_PARAM_FINAL_ESTIMATION = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_FINAL_ESTIMATION";
    private String finalEstimation;
    @BindView(R.id.estimation_tv)
    AppCompatTextView teamMemberEstimationTextView;
    @BindView(R.id.item_points_tv)
    AppCompatTextView itemPointsTextView;
    @BindView(R.id.end_game_button)
    Button endGameButton;
    private PlanningPokerGameEndedListener planningPokerGameEndedListener;


    public static PlanningPokerConclusionGameFragment newInstance(String chosenEstimation) {
        PlanningPokerConclusionGameFragment fragment = new PlanningPokerConclusionGameFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_EXTRA_PARAM_FINAL_ESTIMATION, chosenEstimation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PlanningPokerGameEndedListener) {
            this.planningPokerGameEndedListener = (PlanningPokerGameEndedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.planningPokerGameEndedListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            this.finalEstimation = getArguments().getString(BUNDLE_EXTRA_PARAM_FINAL_ESTIMATION, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_planning_poker_conclusion, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            this.setEstimationValues();
        }
    }

    private void setEstimationValues() {
        this.teamMemberEstimationTextView.setText(this.finalEstimation);
        this.itemPointsTextView.setText(String.format(getResources().getString(R.string.story_points), this.finalEstimation));
    }

    @OnClick(R.id.end_game_button)
    public void finishGame() {
        if (this.planningPokerGameEndedListener != null) {
            this.planningPokerGameEndedListener.finishGame();
        }
    }
}