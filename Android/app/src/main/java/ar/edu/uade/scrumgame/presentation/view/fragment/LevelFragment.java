package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.presenter.LevelPresenter;
import ar.edu.uade.scrumgame.presentation.view.LevelView;
import ar.edu.uade.scrumgame.presentation.view.adapter.SubLevelsAdapter;
import ar.edu.uade.scrumgame.presentation.view.layoutManager.ZoomCenterCardLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LevelFragment extends BaseFragment implements LevelView {

    public interface SubLevelListListener {
        void onSubLevelClicked(String levelName, SubLevelModel subLevelModel);
    }

    @Inject
    LevelPresenter levelPresenter;
    @Inject
    SubLevelsAdapter subLevelsAdapter;
    @BindView(R.id.levelTitle)
    TextView levelTitle;
    @BindView(R.id.levelSubtitle)
    TextView levelSubtitle;
    @BindView(R.id.rv_subLevels)
    RecyclerView subLevelsRecyclerView;
    @BindView(R.id.rl_progress)
    RelativeLayout progressLayout;
    @BindView(R.id.rl_retry)
    RelativeLayout retryLayout;
    @BindView(R.id.bt_retry)
    Button retryButton;
    @BindView(R.id.bt_exit)
    Button exitButton;
    private SubLevelListListener subLevelListListener;
    private String currentLevelName;

    public LevelFragment() {
        this.setRetainInstance(true);
    }

    @OnClick(R.id.bt_exit)
    public void goBack() {
        this.getActivity().onBackPressed();
    }

    @Override
    public void loadLevel(LevelModel level) {
        currentLevelName = level.getName();
        levelTitle.setText(currentLevelName);
        levelSubtitle.setText(String.format("%d subniveles", level.getSublevels().size()));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SubLevelListListener) {
            this.subLevelListListener = (SubLevelListListener) activity;
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
        View fragmentView = inflater.inflate(R.layout.fragment_level, container, false);
        ButterKnife.bind(this, fragmentView);
        this.setUpRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.levelPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadSubLevelList();
        }
    }

    private void setUpRecyclerView() {
        this.subLevelsAdapter.setOnItemClickListener(onItemClickListener);
        ZoomCenterCardLayoutManager horizontalLayoutManager
                = new ZoomCenterCardLayoutManager(context(), LinearLayoutManager.HORIZONTAL, false);
        LinearSnapHelper snapHelper  = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(subLevelsRecyclerView);
        this.subLevelsRecyclerView.setAdapter(subLevelsAdapter);
        this.subLevelsRecyclerView.setLayoutManager(horizontalLayoutManager);
    }

    private void loadSubLevelList() {
        Integer levelCode = getArguments().getInt("levelCode");
        this.levelPresenter.initialize(levelCode);
    }

    @Override
    public void renderSubLevelList(Collection<SubLevelModel> subLevelModelCollection) {
        if (subLevelModelCollection != null) {
            this.subLevelsAdapter.setSubLevelsCollection(subLevelModelCollection);
        }
    }

    @Override
    public void enterSubLevel(String levelName, SubLevelModel subLevelModel) {
        if (this.subLevelListListener!= null) {
            this.subLevelListListener.onSubLevelClicked(levelName, subLevelModel);
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
        this.loadSubLevelList();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.levelPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.levelPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.levelPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.subLevelsRecyclerView.setAdapter(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.subLevelListListener = null;
    }

    private SubLevelsAdapter.OnItemClickListener onItemClickListener =
            subLevelModel -> {
                if (LevelFragment.this.levelPresenter != null && subLevelModel != null) {
                    LevelFragment.this.levelPresenter.onSubLevelClicked(currentLevelName, subLevelModel);
                }
            };

}
