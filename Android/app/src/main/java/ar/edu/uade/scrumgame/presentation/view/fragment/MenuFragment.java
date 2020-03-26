package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.UserOverallDataModel;
import ar.edu.uade.scrumgame.presentation.presenter.MenuPresenter;
import ar.edu.uade.scrumgame.presentation.view.LevelListView;
import ar.edu.uade.scrumgame.presentation.view.adapter.LevelsAdapter;
import ar.edu.uade.scrumgame.presentation.view.adapter.LevelsLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuFragment extends BaseFragment implements LevelListView {
    public interface LevelListListener {
        void onLevelClicked(LevelModel levelModel);
    }

    @Inject
    MenuPresenter menuPresenter;
    @Inject
    LevelsAdapter levelsAdapter;
    @BindView(R.id.rv_levels)
    RecyclerView levelsRecyclerView;
    @BindView(R.id.rl_progress)
    RelativeLayout progressLayout;
    @BindView(R.id.rl_retry)
    RelativeLayout retryLayout;
    @BindView(R.id.bt_retry)
    Button retryButton;
    private LevelListListener levelListListener;

    public MenuFragment() {
        this.setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LevelListListener) {
            this.levelListListener = (LevelListListener) activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(LevelComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, fragmentView);
        this.setUpRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.menuPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadLevelList();
        }
    }

    private void setUpRecyclerView() {
        this.levelsAdapter.setOnItemClickListener(onItemClickListener);
        this.levelsRecyclerView.setLayoutManager(new LevelsLayoutManager(context()));
        this.levelsRecyclerView.setAdapter(levelsAdapter);
    }

    private void loadLevelList() {
        this.menuPresenter.initialize();
    }

    @Override
    public void renderLevelList(Collection<LevelModel> levelModelCollection,
                                Collection<ProgressModel> progressModelCollection,
                                UserOverallDataModel userOverallDataModel) {
        if (levelModelCollection != null) {
            this.levelsAdapter.setLevelsCollection(levelModelCollection, progressModelCollection,
                    userOverallDataModel);
        }
    }

    @Override
    public void enterLevel(LevelModel levelModel) {
        if (this.levelListListener != null) {
            this.levelListListener.onLevelClicked(levelModel);
        }
    }

    @Override
    public void showLoading() {
        this.progressLayout.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.progressLayout.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.retryLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.retryLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        this.showToast(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @OnClick(R.id.bt_retry)
    public void onButtonRetryClick() {
        this.loadLevelList();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.menuPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.menuPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.menuPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.levelsRecyclerView.setAdapter(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.levelListListener = null;
    }

    private LevelsAdapter.OnItemClickListener onItemClickListener =
            levelModel -> {
                if (MenuFragment.this.menuPresenter != null && levelModel != null) {
                    MenuFragment.this.menuPresenter.onLevelClicked(levelModel);
                }
            };

}
