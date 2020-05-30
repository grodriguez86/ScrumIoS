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
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.PlanningPokerNextStepListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanningPokerConfrontationGameFragment extends Fragment {
    private static final String BUNDLE_EXTRA_PARAM_USER_ESTIMATION = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_USER_ESTIMATION";
    private static final String BUNDLE_EXTRA_PARAM_TEAM_MEMBER_ESTIMATION = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_TEAM_MEMBER_ESTIMATION";
    private static final String CHOSEN_ESTIMATION = "8";
    private String userEstimation;
    private String teamMemberEstimation;
    @BindView(R.id.team_member_3_estimation_tv)
    AppCompatTextView teamMemberEstimationTextView;
    @BindView(R.id.user_estimation_tv)
    AppCompatTextView userEstimationTextView;
    @BindView(R.id.continue_button)
    Button continueButton;
    private PlanningPokerNextStepListener planningPokerNextStepListener;


    public static PlanningPokerConfrontationGameFragment newInstance() {
        return new PlanningPokerConfrontationGameFragment();
    }

    public static Fragment newInstance(String userEstimation, String teamMemberEstimation) {
        PlanningPokerConfrontationGameFragment fragment = new PlanningPokerConfrontationGameFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_EXTRA_PARAM_USER_ESTIMATION, userEstimation);
        args.putString(BUNDLE_EXTRA_PARAM_TEAM_MEMBER_ESTIMATION, teamMemberEstimation);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            this.userEstimation = getArguments().getString(BUNDLE_EXTRA_PARAM_USER_ESTIMATION, "");
            this.teamMemberEstimation = getArguments().getString(BUNDLE_EXTRA_PARAM_TEAM_MEMBER_ESTIMATION, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_planning_poker_confrontation, container, false);
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
        this.userEstimationTextView.setText(this.userEstimation);
        this.teamMemberEstimationTextView.setText(this.teamMemberEstimation);
    }

    @OnClick(R.id.continue_button)
    public void continueToNextStep() {
        if (this.planningPokerNextStepListener != null) {
            Fragment conclusionFragment = PlanningPokerConclusionGameFragment.newInstance(CHOSEN_ESTIMATION);
            this.planningPokerNextStepListener.goToNextStep(conclusionFragment);
        }
    }
}