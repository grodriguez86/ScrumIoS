package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.adapter.ShortTextQuizAdapter;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import ar.edu.uade.scrumgame.presentation.view.utils.GameModelUtils;
import butterknife.BindView;

public class ShortTextQuizGameFragment extends GameFragment implements GameContentView, ShortTextQuizAdapter.OnItemClickListener {
    @BindView(R.id.options_rv)
    RecyclerView optionsRecyclerView;
    private ShortTextQuizAdapter optionsAdapter;
    private Boolean isChoiceCorrect = false;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_short_text_quiz;
    }

    @Override
    protected void doLoadGame() {
        this.setUpRecyclerView();
    }

    @Override
    protected void doLoadCompletedGame() {
        int correctOptionIndex = GameModelUtils.getCorrectOptionIndex(infoGameModel.getContent());
        if (this.optionsRecyclerView != null) {
            new Handler().postDelayed(() -> {
                RecyclerView.ViewHolder viewHolder = this.optionsRecyclerView.findViewHolderForAdapterPosition(correctOptionIndex);
                if (viewHolder != null) {
                    viewHolder.itemView.findViewById(R.id.selected_rb).performClick();
                }
                this.optionsAdapter.setEnabled(false);
                this.optionsRecyclerView.setEnabled(false);
            }, 100);
        }
    }

    private void setUpRecyclerView() {
        this.optionsAdapter = new ShortTextQuizAdapter(getActivity());
        this.optionsAdapter.setGameContentModels(infoGameModel.getContent());
        this.optionsAdapter.setOnItemClickListener(this);
        this.optionsRecyclerView.setAdapter(this.optionsAdapter);
        this.optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    }

    @Override
    public void onCorrectAttempt() {
        super.onCorrectAttempt();
        if (this.onGameCompletedListener != null) {
            this.showAlert(getString(R.string.correct_answer_title), getString(R.string.correct_answer), getActivity(), getString(R.string.correct_answer_button_text), (dialog, which) -> this.onGameCompletedListener.onGameCompleted(gameCode));
        }
    }

    @Override
    public void onFailedAttempt() {
        super.onFailedAttempt();
        this.showAlert(getString(R.string.incorrect_answer_title), getString(R.string.incorrect_answer), getActivity(), getString(R.string.incorrect_answer_button_text), (dialog, which) -> {
        });
    }

    @Override
    public void checkAttempt() {
        super.checkAttempt();
        if (this.isChoiceCorrect != null) {
            if (isChoiceCorrect) {
                onCorrectAttempt();
            } else {
                onFailedAttempt();
            }
        }
    }

    @Override
    public void onOptionSelected(GameContentModel gameContentModel) {
        this.isChoiceCorrect = gameContentModel.getCorrect();
    }
}
