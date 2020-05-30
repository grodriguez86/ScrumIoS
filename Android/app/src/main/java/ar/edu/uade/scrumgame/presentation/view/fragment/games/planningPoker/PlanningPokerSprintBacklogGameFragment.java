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
import ar.edu.uade.scrumgame.presentation.models.ProductBacklogItemModel;
import ar.edu.uade.scrumgame.presentation.view.adapter.PlanningPokerFinalProductBacklogAdapter;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.PlanningPokerGameEndedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PlanningPokerSprintBacklogGameFragment extends Fragment {
    private PlanningPokerFinalProductBacklogAdapter adapter;
    @BindView(R.id.product_backlog_rv)
    RecyclerView productBacklogRecyclerView;
    @BindView(R.id.end_game_button)
    Button endGameButton;
    private PlanningPokerGameEndedListener planningPokerGameEndedListener;


    public static PlanningPokerSprintBacklogGameFragment newInstance() {
        return new PlanningPokerSprintBacklogGameFragment();
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
        if (savedInstanceState == null && this.getActivity() != null) {
            this.adapter = new PlanningPokerFinalProductBacklogAdapter(this.getActivity());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_planning_poker_sprint_backlog, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            this.setUpRecyclerView();
        }
    }

    private void setUpRecyclerView() {
        this.createProductBacklogItems();
        this.productBacklogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        this.productBacklogRecyclerView.setAdapter(adapter);
    }

    private void createProductBacklogItems() {
        List<ProductBacklogItemModel> productBacklog = new LinkedList<>();
        List<String> productBacklogItems = Arrays.asList(getResources().getStringArray(R.array.planning_poker_product_backlog_items));
        productBacklog.add(new ProductBacklogItemModel(productBacklogItems.get(0), "8", true));
        productBacklog.add(new ProductBacklogItemModel(productBacklogItems.get(1), "13", true));
        productBacklog.add(new ProductBacklogItemModel(productBacklogItems.get(2), "5", false));
        productBacklog.add(new ProductBacklogItemModel(productBacklogItems.get(3), "1", false));
        adapter.setGameContentModels(productBacklog);
    }


    @OnClick(R.id.end_game_button)
    public void finishGame() {
        if (this.planningPokerGameEndedListener != null) {
            this.planningPokerGameEndedListener.finishGame();
        }
    }
}