package ar.edu.uade.scrumgame.presentation.view.fragment.games.velocity;

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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VelocityResultsGameFragment extends Fragment {
    public interface OnContinueToConclusionListener {
        void continueToConclusion();
    }

    private static final String BUNDLE_EXTRA_PARAM_EXPECTED_SCORE = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_EXPECTED_SCORE";
    private static final String BUNDLE_EXTRA_PARAM_SCORE = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_SCORE";
    @BindView(R.id.expectedScoreTv)
    AppCompatTextView expectedScoreTv;
    @BindView(R.id.scoreTv)
    AppCompatTextView scoreTv;
    @BindView(R.id.resultsDescriptionTv)
    AppCompatTextView resultsDescriptionTv;
    @BindView(R.id.continueBtn)
    Button continueButton;
    private int score;
    private int expectedScore;
    private OnContinueToConclusionListener onContinueToConclusionListener;

    public static VelocityResultsGameFragment newInstance(int expectedScore, int score) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_EXTRA_PARAM_EXPECTED_SCORE, expectedScore);
        bundle.putInt(BUNDLE_EXTRA_PARAM_SCORE, score);
        VelocityResultsGameFragment fragment = new VelocityResultsGameFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnContinueToConclusionListener) {
            this.onContinueToConclusionListener = (OnContinueToConclusionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onContinueToConclusionListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getGamesData();
    }

    private void getGamesData() {
        if (this.getArguments() != null) {
            this.score = this.getArguments().getInt(BUNDLE_EXTRA_PARAM_SCORE, 0);
            this.expectedScore = this.getArguments().getInt(BUNDLE_EXTRA_PARAM_EXPECTED_SCORE, 0);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.expectedScoreTv.setText(String.valueOf(this.expectedScore));
        this.scoreTv.setText(String.valueOf(this.score));
        this.resultsDescriptionTv.setText(getString(this.getScoreDescription()));
    }

    private int getScoreDescription() {
        if (this.score > this.expectedScore) {
            return R.string.velocity_game_lower_estimation;
        }
        if (this.score < this.expectedScore) {
            return R.string.velocity_game_higher_estimation;
        }
        return R.string.velocity_game_exact_estimation;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_velocity_results, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.continueBtn)
    public void onContinueButtonClicked() {
        if (this.onContinueToConclusionListener != null) {
            this.onContinueToConclusionListener.continueToConclusion();
        }
    }

}