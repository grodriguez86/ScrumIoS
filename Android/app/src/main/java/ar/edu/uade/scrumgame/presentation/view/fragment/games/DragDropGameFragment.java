package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.adapter.DragDropAdapter;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import butterknife.BindView;

public class DragDropGameFragment extends GameFragment implements GameContentView {

    @BindView(R.id.options_rv)
    RecyclerView optionsRecyclerView;
    @BindView(R.id.answers_rv)
    RecyclerView answersRecyclerView;
    private DragDropAdapter answersAdapter;
    private List<GameContentModel> correctAnswers;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_drag_drop;
    }

    @Override
    protected void doLoadGame() {
        this.setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        this.correctAnswers = this.filterCorrectAnswers(infoGameModel.getContent());
        DragDropAdapter optionsAdapter = new DragDropAdapter(getActivity());
        optionsAdapter.setGameContentModels(infoGameModel.getContent());
        this.answersAdapter = new DragDropAdapter(getActivity());
        this.optionsRecyclerView.setAdapter(optionsAdapter);
        this.optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        this.optionsRecyclerView.setOnDragListener(optionsAdapter.getDragListenerInstance());
        this.answersRecyclerView.setAdapter(answersAdapter);
        this.answersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        this.answersRecyclerView.setOnDragListener(answersAdapter.getDragListenerInstance());
    }

    private List<GameContentModel> filterCorrectAnswers(List<GameContentModel> content) {
        List<GameContentModel> correctAnswers = new LinkedList<>();
        for (GameContentModel gameContentModel : content) {
            if (gameContentModel.getCorrect()) {
                correctAnswers.add(gameContentModel);
            }
        }
        return correctAnswers;
    }


    @Override
    public void onCorrectAttempt() {
        this.showAlert(getString(R.string.correct_answer_title), getString(R.string.correct_answer), getActivity(), getString(R.string.correct_answer_button_text), (dialog, which) -> this.onGameCompletedListener.onGameCompleted(gameCode));
    }

    @Override
    public void onFailedAttempt() {
        this.showAlert(getString(R.string.incorrect_answer_title), getString(R.string.incorrect_answer), getActivity(), getString(R.string.incorrect_answer_button_text), (dialog, which) -> {
        });
    }

    @Override
    public void checkAttempt() {
        if (this.hasWrongAnswers(this.answersAdapter)) {
            this.onFailedAttempt();
        } else {
            this.onCorrectAttempt();
        }
    }

    private boolean hasWrongAnswers(DragDropAdapter answersAdapter) {
        List<GameContentModel> answers = answersAdapter.getItems();
        if (answers.size() == this.correctAnswers.size()) {
            for (GameContentModel answer : answers) {
                if (!answer.getCorrect()) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}