package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.InfoTheoryModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.presenter.InfoTheoryPresenter;
import ar.edu.uade.scrumgame.presentation.view.InfoTheoryView;
import ar.edu.uade.scrumgame.presentation.view.adapter.InfoTheoryAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoTheoryFragment extends BaseFragment implements InfoTheoryView {

    public interface SubLevelListListener {
        void onSubLevelClicked(SubLevelModel subLevelModel);
    }

    @Inject
    InfoTheoryPresenter infoTheoryPresenter;
    @Inject
    InfoTheoryAdapter infoTheoryAdapter;
    @BindView(R.id.levelTitle)
    TextView levelTitle;
    @BindView(R.id.subLevelTitle)
    TextView subLevelTitle;
    @BindView(R.id.rv_infoTheory)
    RecyclerView infoTheoryRecyclerView;
    @BindView(R.id.rl_progress)
    RelativeLayout progressLayout;
    @BindView(R.id.rl_retry)
    RelativeLayout retryLayout;
    @BindView(R.id.bt_retry)
    Button retryButton;
    @BindView(R.id.bt_exit)
    Button exitButton;

    private SubLevelListListener subLevelListListener;

    public InfoTheoryFragment() {
        this.setRetainInstance(true);
    }

    @OnClick(R.id.bt_exit)
    public void goBack() {
        this.getActivity().onBackPressed();
    }

    @Override
    public void loadSubLevel(SubLevelModel subLevelModel) {
        subLevelTitle.setText(subLevelModel.getName());
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
        View fragmentView = inflater.inflate(R.layout.fragment_info_theory, container, false);
        ButterKnife.bind(this, fragmentView);
        setUpRecyclerView();
        return fragmentView;
    }

    private void setUpRecyclerView() {
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(context(), LinearLayoutManager.HORIZONTAL, false);
        this.infoTheoryRecyclerView.setAdapter(infoTheoryAdapter);
        this.infoTheoryRecyclerView.setLayoutManager(horizontalLayoutManager);
        horizontalLayoutManager.findFirstVisibleItemPosition();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.infoTheoryPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadSubLevel();
        }
    }

    private void loadSubLevel() {
        String levelName = getArguments().getString("levelName");
        levelTitle.setText(levelName);
        String subLevelCode = getArguments().getString("subLevelCode");
        this.infoTheoryPresenter.initialize(subLevelCode);
    }

    @Override
    public void renderInfoTheoryList(Collection<InfoTheoryModel> infoTheoryModelCollection) {
        if (infoTheoryModelCollection!= null) {
            this.infoTheoryAdapter.setInfoTheoryCollection(infoTheoryModelCollection);
        }
    }

    @Override
    public void enterSubLevel(SubLevelModel subLevelModel) {
        if (this.subLevelListListener!= null) {
            this.subLevelListListener.onSubLevelClicked(subLevelModel);
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
        this.loadSubLevel();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.infoTheoryPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.infoTheoryPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.infoTheoryPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.subLevelListListener = null;
    }

}
