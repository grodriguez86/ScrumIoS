package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.adapter.ShortTextQuizAdapter;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import butterknife.BindView;

public class ShortTextQuizGameFragment extends GameFragment implements GameContentView, ShortTextQuizAdapter.OnItemClickListener {
    @BindView(R.id.options_rv)
    RecyclerView optionsRecyclerView;
    private Boolean isChoiceCorrect = false;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_short_text_quiz;
    }

    @Override
    protected void doLoadGame() {
        this.setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        ShortTextQuizAdapter optionsAdapter = new ShortTextQuizAdapter(getActivity());
        optionsAdapter.setGameContentModels(infoGameModel.getContent());
        optionsAdapter.setOnItemClickListener(this);
        this.optionsRecyclerView.setAdapter(optionsAdapter);
        this.optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    }

    @Override
    public void onCorrectAttempt() {
        super.onCorrectAttempt();
        this.showAlert(getString(R.string.correct_answer_title), getString(R.string.correct_answer), getActivity(), getString(R.string.correct_answer_button_text), (dialog, which) -> this.onGameCompletedListener.onGameCompleted(gameCode));
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