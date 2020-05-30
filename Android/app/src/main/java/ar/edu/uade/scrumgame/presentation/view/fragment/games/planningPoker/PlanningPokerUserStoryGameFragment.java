package ar.edu.uade.scrumgame.presentation.view.fragment.games.planningPoker;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.PlanningPokerNextStepListener;
import ar.edu.uade.scrumgame.presentation.view.utils.ViewIdGenerator;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.Arrays;
import java.util.List;

public class PlanningPokerUserStoryGameFragment extends Fragment {
    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    @BindView(R.id.estimation_tl)
    TableLayout estimationTable;
    private PlanningPokerNextStepListener planningPokerNextStepListener;


    public static PlanningPokerUserStoryGameFragment newInstance() {
        return new PlanningPokerUserStoryGameFragment();
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
        View fragmentView = inflater.inflate(R.layout.fragment_game_planning_poker_user_story, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            List<String> fibonacciSequence = Arrays.asList(getResources().getStringArray(R.array.fibonacci_sequence));
            this.createTable(fibonacciSequence);
        }
    }

    private void createTable(List<String> values) {
        for (int index = 0; index < ROWS; index++) {
            this.createRow(values.subList(index*COLUMNS, (index*COLUMNS) +COLUMNS));
        }
    }

    private void createRow(List<String> rowValues) {
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1);
        TableRow tableRow = new TableRow(getActivity());
        tableRow.setId(ViewIdGenerator.generateViewId());
        tableRow.setLayoutParams(rowLayout);
        for (String rowValue : rowValues) {
            this.createButton(rowValue, tableRow);
        }
        this.estimationTable.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT,1));
    }

    private void createButton(String rowValue, TableRow tableRow) {
        if (this.getActivity() != null) {
            Button button = new Button(getActivity());
            button.setId(ViewIdGenerator.generateViewId());
            TableRow.LayoutParams buttonLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1);
            buttonLayoutParams.setMargins(2,2,2,2);
            button.setLayoutParams(buttonLayoutParams);
            button.setText(rowValue);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            button.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_round_green_background));
            button.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            button.setOnClickListener(v ->
                    this.continueToNextStep(rowValue)
            );
            tableRow.addView(button);
        }
    }

    private void continueToNextStep(String selectedValue) {
        if (this.planningPokerNextStepListener != null) {
            Fragment cardsShownFragment = PlanningPokerCardsShownGameFragment.newInstance(selectedValue);
            this.planningPokerNextStepListener.goToNextStep(cardsShownFragment);
        }
    }
}