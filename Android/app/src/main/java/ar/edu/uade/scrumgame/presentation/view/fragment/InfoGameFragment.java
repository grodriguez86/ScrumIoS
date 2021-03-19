package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.Nullable;

import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.constants.GameFragmentConstant;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.presenter.InfoGamesContentPresenter;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.InfoGamesContentView;
import ar.edu.uade.scrumgame.presentation.view.activity.InfoGameActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoGameFragment extends BaseFragment implements InfoGamesContentView {
    public interface OnGamesCompletedListener {
        void onGamesCompleted();
    }

    private static final String BUNDLE_EXTRA_PARAM_SUB_LEVEL_CODE = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_SUB_LEVEL_CODE";
    private static final String BUNDLE_EXTRA_PARAM_LEVEL_TITLE = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_LEVEL_TITLE";
    private static final String BUNDLE_EXTRA_PARAM_SUB_LEVEL_TITLE = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_SUB_LEVEL_TITLE";
    private static final String BUNDLE_EXTRA_PARAM_INFO_GAME = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_INFO_GAME";
    private static final String BUNDLE_EXTRA_PARAM_COMPLETED = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_COMPLETED";
    @BindView(R.id.game_progress)
    ProgressBar progressBar;
    @BindView(R.id.progress_tv)
    AppCompatTextView progressAppCompatTextView;
    @BindView(R.id.levelTitle)
    AppCompatTextView levelTitleAppCompatTextView;
    @BindView(R.id.subLevelTitle)
    AppCompatTextView subLevelTitleAppCompatTextView;
    @BindView(R.id.title)
    AppCompatTextView titleAppCompatTextView;
    @BindView(R.id.indications)
    AppCompatTextView indicationsAppCompatTextView;
    @BindView(R.id.sendAnswer)
    Button sendAnswerButton;
    @BindView(R.id.rl_progress)
    FrameLayout progressLayout;
    @BindView(R.id.rl_retry)
    FrameLayout retryLayout;
    @BindView(R.id.bt_retry)
    Button retryButton;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheetLayout;
    @BindView(R.id.finish_game_btn)
    Button finishGameButton;
    @BindView(R.id.btn_back)
    ImageButton backButton;
    private Fragment gameFragment;
    @Inject
    InfoGamesContentPresenter infoGamesContentPresenter;
    private InfoGameActivity context;
    private String subLevelCode;
    private String levelTitle;
    private String subLevelTitle;
    private InfoGameModel currentGame;
    private OnGamesCompletedListener onGamesCompletedListener;
    private BottomSheetBehavior bottomSheetBehavior;

    public static InfoGameFragment newInstance(String levelTitle, String subLevelCode, String subLevelTitle) {
        InfoGameFragment fragment = new InfoGameFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_EXTRA_PARAM_SUB_LEVEL_CODE, subLevelCode);
        bundle.putString(BUNDLE_EXTRA_PARAM_LEVEL_TITLE, levelTitle);
        bundle.putString(BUNDLE_EXTRA_PARAM_SUB_LEVEL_TITLE, subLevelTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        this.getComponent(LevelComponent.class).inject(this);
        super.onAttach(activity);
        if (activity instanceof InfoGameActivity) {
            this.context = (InfoGameActivity) activity;
        }
        if (activity instanceof OnGamesCompletedListener) {
            this.onGamesCompletedListener = (OnGamesCompletedListener) activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getGamesData();
    }

    private void getGamesData() {
        this.subLevelCode = getArguments().getString(BUNDLE_EXTRA_PARAM_SUB_LEVEL_CODE);
        this.levelTitle = getArguments().getString(BUNDLE_EXTRA_PARAM_LEVEL_TITLE);
        this.subLevelTitle = getArguments().getString(BUNDLE_EXTRA_PARAM_SUB_LEVEL_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_info_game, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.infoGamesContentPresenter.setView(this);
        this.levelTitleAppCompatTextView.setText(levelTitle);
        this.subLevelTitleAppCompatTextView.setText(subLevelTitle);
        this.bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        if (savedInstanceState == null) {
            this.loadInfoGames();
        }
    }

    private void loadInfoGames() {
        this.infoGamesContentPresenter.initialize(this.subLevelCode);
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

    @OnClick(R.id.bt_retry)
    public void onButtonRetryClick() {
        this.loadInfoGames();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.infoGamesContentPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.infoGamesContentPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.infoGamesContentPresenter.destroy();
    }

    @Override
    public void playGame(InfoGameModel game, Float progress) {
        this.currentGame = game;
        this.updateProgress(progress);
        this.loadGame(game);
    }

    @Override
    public void showLevelCompletedView() {
        this.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.sendAnswer)
    public void onSendAnswerClicked() {
        if (this.infoGamesContentPresenter.isGameCompleted() && this.currentGame != null) {
            this.onCompleteGame(this.currentGame.getCode());
        } else if (gameFragment != null && gameFragment instanceof GameContentView) {
            ((GameContentView) gameFragment).checkAttempt();
        }
    }

    @Override
    public void onCompleteGame(String gameCode) {
        this.infoGamesContentPresenter.playNextLevel(gameCode);
    }

    private void updateProgress(Float progress) {
        int progressPercentage = progress.intValue();
        this.progressBar.setProgress(progressPercentage);
        this.progressAppCompatTextView.setText(getString(R.string.progress_label, progressPercentage));
    }

    private void loadGame(InfoGameModel infoGameModel) {
        this.setUpView(infoGameModel);
        this.setFragment(infoGameModel);
    }

    private void setFragment(InfoGameModel infoGameModel) {
        String fragmentName = GameFragmentConstant.getFragmentNameForType(infoGameModel.getType());
        try {
            gameFragment = (Fragment) Class.forName(fragmentName).newInstance();
            Bundle bundle = new Bundle();
            bundle.putParcelable(BUNDLE_EXTRA_PARAM_INFO_GAME, infoGameModel);
            bundle.putBoolean(BUNDLE_EXTRA_PARAM_COMPLETED, this.infoGamesContentPresenter.isGameCompleted());
            gameFragment.setArguments(bundle);
            if (context != null) {
                addFragment(gameFragment);
            }
        } catch (ClassNotFoundException cne) {
            Log.e(cne.getCause().getMessage(), cne.getMessage(), cne);
        } catch (IllegalAccessException iae) {
            Log.e(iae.getCause().getMessage(), iae.getMessage(), iae);
        } catch (java.lang.InstantiationException ie) {
            Log.e(ie.getCause().getMessage(), ie.getMessage(), ie);
        }
    }

    private void setUpView(InfoGameModel infoGameModel) {
        titleAppCompatTextView.setText(infoGameModel.getTitle());
        if (this.shouldHideSendAnswerButton(infoGameModel.getType())) {
            sendAnswerButton.setVisibility(View.GONE);
        } else {
            sendAnswerButton.setVisibility(View.VISIBLE);
        }
        indicationsAppCompatTextView.setVisibility(View.GONE);
        this.backButton.setVisibility(this.infoGamesContentPresenter.isFirstGame() ? View.INVISIBLE : View.VISIBLE);
    }

    private boolean shouldHideSendAnswerButton(String type) {
        String[] hideSendAnswerButtonGames = getResources().getStringArray(R.array.no_send_button_games);
        for (String game : hideSendAnswerButtonGames) {
            if (game.equals(type)) {
                return true;
            }
        }
        return false;
    }

    protected void addFragment(Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.gameContainer, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @OnClick(R.id.bt_exit)
    public void exitGame() {
        if (this.getActivity() != null) {
            this.getActivity().onBackPressed();
        }
    }

    @OnClick(R.id.finish_game_btn)
    public void finishGame() {
        if (this.onGamesCompletedListener != null) {
            this.infoGamesContentPresenter.finishSublevel();
        }
        this.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void goToSublevelMenu() {
        this.onGamesCompletedListener.onGamesCompleted();
    }

    @OnClick(R.id.btn_back)
    public void goToPreviousGame() {
        this.infoGamesContentPresenter.playPreviousLevel();
    }

}
