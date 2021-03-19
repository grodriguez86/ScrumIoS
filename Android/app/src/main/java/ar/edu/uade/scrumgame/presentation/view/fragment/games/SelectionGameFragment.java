package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.adapter.SelectionAdapter;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import ar.edu.uade.scrumgame.presentation.view.utils.GameModelUtils;
import butterknife.BindView;

import java.util.List;

public class SelectionGameFragment extends GameFragment implements GameContentView {

    @BindView(R.id.options_rv)
    RecyclerView optionsRecyclerView;
    private SelectionAdapter optionsAdapter;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_selection;
    }

    @Override
    protected void doLoadGame() {
        this.setUpRecyclerView();
    }

    @Override
    protected void doLoadCompletedGame() {
        List<Integer> correctOptionIndexes = GameModelUtils.getCorrectOptionIndexes(infoGameModel.getContent());
        if (this.optionsRecyclerView != null) {
            new Handler().postDelayed(() -> {
                for (Integer correctOptionIndex : correctOptionIndexes) {
                    RecyclerView.ViewHolder viewHolder = this.optionsRecyclerView.findViewHolderForAdapterPosition(correctOptionIndex);
                    if (viewHolder != null) {
                        viewHolder.itemView.performClick();
                    }
                }
                this.optionsAdapter.setEnabled(false);
            }, 100);
        }
    }

    private void setUpRecyclerView() {
        this.optionsAdapter = new SelectionAdapter(getActivity());
        this.optionsAdapter.setGameContentModels(infoGameModel.getContent());
        this.optionsRecyclerView.setAdapter(optionsAdapter);
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
        if (this.optionsAdapter != null) {
            if (this.hasWrongAnswers(optionsAdapter.getSelectedOptions())) {
                this.onFailedAttempt();
            } else {
                this.onCorrectAttempt();
            }
        }
    }

    private boolean hasWrongAnswers(List<GameContentModel> selectedOptions) {
        for (GameContentModel selectedOption : selectedOptions) {
            if (!selectedOption.getCorrect()) {
                return true;
            }

        }
        return false;
    }
}
