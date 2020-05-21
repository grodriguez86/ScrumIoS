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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.view.adapter.PlanningPokerProductBacklogAdapter;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.PlanningPokerNextStepListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanningPokerIntroGameFragment extends Fragment {
    private static final String BUNDLE_EXTRA_PARAM_INFO_GAME_MODEL = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_INFO_GAME_MODE";
    private PlanningPokerProductBacklogAdapter productBacklogAdapter;
    private InfoGameModel infoGameModel;
    @BindView(R.id.product_backlog_rv)
    RecyclerView productBacklogRecyclerView;
    @BindView(R.id.continue_button)
    Button continueButton;
    private PlanningPokerNextStepListener planningPokerNextStepListener;


    public static PlanningPokerIntroGameFragment newInstance(InfoGameModel infoGameModel) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_EXTRA_PARAM_INFO_GAME_MODEL, infoGameModel);
        PlanningPokerIntroGameFragment fragment = new PlanningPokerIntroGameFragment();
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
        if (this.getArguments() != null && this.getActivity()!=null) {
            this.infoGameModel = getArguments().getParcelable(BUNDLE_EXTRA_PARAM_INFO_GAME_MODEL);
            this.productBacklogAdapter = new PlanningPokerProductBacklogAdapter(getActivity());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_planning_poker_intro, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        productBacklogAdapter.setGameContentModels(infoGameModel.getContent());
        this.productBacklogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        this.productBacklogRecyclerView.setAdapter(productBacklogAdapter);
    }

    @OnClick(R.id.continue_button)
    public void continueToNextStep() {
        if (this.planningPokerNextStepListener != null) {
            Fragment pivotFragment = PlanningPokerPivotGameFragment.newInstance();
            this.planningPokerNextStepListener.goToNextStep(pivotFragment);
        }
    }
}