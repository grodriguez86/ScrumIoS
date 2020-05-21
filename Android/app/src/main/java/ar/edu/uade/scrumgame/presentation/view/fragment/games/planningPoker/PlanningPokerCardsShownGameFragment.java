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

import java.util.Arrays;
import java.util.List;

public class PlanningPokerCardsShownGameFragment extends Fragment {
    private static final String BUNDLE_EXTRA_PARAM_SELECTED_VALUE = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_SELECTED_VALUE";
    private static final int TEAM_MEMBER_1_ESTIMATION_1 = 13;
    private static final int TEAM_MEMBER_2_ESTIMATION_1 = 20;
    private static final int TEAM_MEMBER_3_ESTIMATION_1 = 40;
    private static final int TEAM_MEMBER_1_ESTIMATION_2 = 5;
    private static final int TEAM_MEMBER_2_ESTIMATION_2 = 5;
    private static final int TEAM_MEMBER_3_ESTIMATION_2 = 1;
    @BindView(R.id.user_estimation_tv)
    AppCompatTextView userEstimation;
    @BindView(R.id.team_member_1_estimation_tv)
    AppCompatTextView teamMember1Estimation;
    @BindView(R.id.team_member_2_estimation_tv)
    AppCompatTextView teamMember2Estimation;
    @BindView(R.id.team_member_3_estimation_tv)
    AppCompatTextView teamMember3Estimation;
    @BindView(R.id.continue_button)
    Button continueButton;
    private String selectedValue;
    private PlanningPokerNextStepListener planningPokerNextStepListener;


    public static PlanningPokerCardsShownGameFragment newInstance(String selectedValue) {
        PlanningPokerCardsShownGameFragment fragment = new PlanningPokerCardsShownGameFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_EXTRA_PARAM_SELECTED_VALUE, selectedValue);
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
            this.selectedValue = getArguments().getString(BUNDLE_EXTRA_PARAM_SELECTED_VALUE, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_planning_poker_cards_shown, container, false);
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
        this.userEstimation.setText(this.selectedValue);
        List<String> fibonacciSequence = Arrays.asList(getResources().getStringArray(R.array.fibonacci_sequence));
        List<String> lowerValues = fibonacciSequence.subList(0, 7);
        if (lowerValues.contains(this.selectedValue)) {
            this.teamMember1Estimation.setText(String.valueOf(TEAM_MEMBER_1_ESTIMATION_1));
            this.teamMember2Estimation.setText(String.valueOf(TEAM_MEMBER_2_ESTIMATION_1));
            this.teamMember3Estimation.setText(String.valueOf(TEAM_MEMBER_3_ESTIMATION_1));
        } else {
            this.teamMember1Estimation.setText(String.valueOf(TEAM_MEMBER_1_ESTIMATION_2));
            this.teamMember2Estimation.setText(String.valueOf(TEAM_MEMBER_2_ESTIMATION_2));
            this.teamMember3Estimation.setText(String.valueOf(TEAM_MEMBER_3_ESTIMATION_2));
        }
    }

    @OnClick(R.id.continue_button)
    public void continueToNextStep() {
        if (this.planningPokerNextStepListener != null) {
            Fragment confrontationFragment = PlanningPokerConfrontationGameFragment.newInstance(this.selectedValue,this.teamMember3Estimation.getText().toString());
            this.planningPokerNextStepListener.goToNextStep(confrontationFragment);
        }
    }
}