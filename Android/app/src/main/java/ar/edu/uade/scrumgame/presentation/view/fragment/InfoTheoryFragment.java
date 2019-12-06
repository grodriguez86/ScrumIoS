package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    public interface PlaySubLevelListener {
        void onPlayClicked(Integer levelCode, String levelTitle, String subLevelCode, String subLevelTitle);
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
    @BindView(R.id.btn_back)
    ImageButton backButton;
    @BindView(R.id.btn_next)
    ImageButton nextButton;
    @BindView(R.id.btn_skip)
    Button skipButton;
    @BindView(R.id.btn_play)
    Button playButton;
    @BindView(R.id.pageIndicator)
    LinearLayout pageIndicator;
    List<ImageView> pageIndicatorItems;

    private Integer levelCode;
    private String subLevelCode;

    private PlaySubLevelListener playSubLevelListener;

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
        if (activity instanceof PlaySubLevelListener) {
            this.playSubLevelListener = (PlaySubLevelListener) activity;
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
                = new LinearLayoutManager(context(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public void onItemsChanged(@NonNull RecyclerView recyclerView) {
                pageIndicator.removeAllViews();
                pageIndicatorItems = new ArrayList<>();
                int horizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        4, getResources().getDisplayMetrics());
                for (int i = 0; i < this.getItemCount(); i++) {
                    ImageView item = new ImageView(context());
                    item.setImageDrawable(getResources().getDrawable(R.drawable.blue_progress));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    item.setAdjustViewBounds(true);
                    item.setPadding(horizontalPadding, 0, horizontalPadding, 0);
                    pageIndicator.addView(item, layoutParams);
                    pageIndicatorItems.add(item);
                }
                updatePageIndicator();
            }
        };
        this.infoTheoryRecyclerView.setAdapter(infoTheoryAdapter);
        this.infoTheoryRecyclerView.setLayoutManager(horizontalLayoutManager);
        this.infoTheoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateBottomButtons(horizontalLayoutManager);
                updatePageIndicator();
            }
        });
        horizontalLayoutManager.findFirstVisibleItemPosition();
        this.updateBottomButtons(horizontalLayoutManager);
    }

    private void updatePageIndicator() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) infoTheoryRecyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            Drawable selectedDrawable = getResources().getDrawable(R.drawable.page_indicator_item_white);
            Drawable unselectedDrawable = getResources().getDrawable(R.drawable.page_indicator_item_blue);
            for (int i = 0; i < pageIndicatorItems.size(); i++) {
                if (i == linearLayoutManager.findFirstVisibleItemPosition())
                    pageIndicatorItems.get(i).setImageDrawable(selectedDrawable);
                else
                    pageIndicatorItems.get(i).setImageDrawable(unselectedDrawable);
            }
        }
    }

    private void updateBottomButtons(LinearLayoutManager recyclerViewLayoutManager) {
        int currentPosition = recyclerViewLayoutManager.findFirstVisibleItemPosition();
        if (currentPosition == 0)
            backButton.setVisibility(View.INVISIBLE);
        else
            backButton.setVisibility(View.VISIBLE);
        if (currentPosition == recyclerViewLayoutManager.getItemCount() - 1) {
            nextButton.setVisibility(View.GONE);
            playButton.setVisibility(View.VISIBLE);
            skipButton.setVisibility(View.INVISIBLE);
        } else {
            playButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
            skipButton.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_next)
    public void nextPage() {
        LinearLayoutManager recyclerViewLayoutManager = (LinearLayoutManager) infoTheoryRecyclerView.getLayoutManager();
        if (recyclerViewLayoutManager != null) {
            int currentPosition = recyclerViewLayoutManager.findFirstVisibleItemPosition();
            if (currentPosition < (recyclerViewLayoutManager.getItemCount() - 1))
                infoTheoryRecyclerView.scrollToPosition(++currentPosition);
            else
                startPlaying();
        }
    }

    @OnClick(R.id.btn_back)
    public void previousPage() {
        LinearLayoutManager recyclerViewLayoutManager = (LinearLayoutManager) infoTheoryRecyclerView.getLayoutManager();
        if (recyclerViewLayoutManager != null) {
            int currentPosition = recyclerViewLayoutManager.findFirstVisibleItemPosition();
            if (currentPosition > 0)
                infoTheoryRecyclerView.scrollToPosition(--currentPosition);
        }
    }

    @OnClick(R.id.btn_skip)
    public void skipTutorial() {
        startPlaying();
    }

    @OnClick(R.id.btn_play)
    public void startPlaying() {
        if (levelCode != null && subLevelCode != null)
            this.playSubLevel(levelCode, levelTitle.getText().toString(), subLevelCode, subLevelTitle.getText().toString());
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
        levelCode = getArguments().getInt("levelCode");
        levelTitle.setText(levelName);
        subLevelCode = getArguments().getString("subLevelCode");
        this.infoTheoryPresenter.initialize(subLevelCode);
    }

    @Override
    public void renderInfoTheoryList(Collection<InfoTheoryModel> infoTheoryModelCollection) {
        if (infoTheoryModelCollection != null) {
            this.infoTheoryAdapter.setInfoTheoryCollection(infoTheoryModelCollection);
        }
    }

    @Override
    public void playSubLevel(Integer levelCode, String levelTitle, String subLevelCode, String subLevelTitle) {
        if (this.playSubLevelListener != null) {
            this.playSubLevelListener.onPlayClicked(levelCode, levelTitle, subLevelCode, subLevelTitle);
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
        this.playSubLevelListener = null;
    }

}
