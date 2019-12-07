package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.widget.Button;

import java.util.List;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import butterknife.BindView;

public class TrueFalseGameFragment extends GameFragment implements GameContentView {
    private static final int NUMBER_OF_CHOICES = 2;
    @BindView(R.id.choice_1_btn)
    Button choice1Button;
    @BindView(R.id.choice_2_btn)
    Button choice2Button;
    private Button choiceAttempt;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_choice_true_false;
    }

    @Override
    protected void doLoadGame() {
        Button[] choices = new Button[]{choice1Button, choice2Button};
        List<GameContentModel> gameContent = infoGameModel.getContent();
        if (gameContent.size() != NUMBER_OF_CHOICES) {
            throw new IllegalArgumentException("True/false choices must be two");
        }
        for (int index = 0; index < NUMBER_OF_CHOICES; index++) {
            choices[index].setText(gameContent.get(index).getData());
            choices[index].setTag(gameContent.get(index).getCorrect());
            choices[index].setOnClickListener(v -> {
                this.choiceAttempt = (Button) v;
                checkAttempt();
            });
        }
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
        if (this.choiceAttempt != null) {
            Boolean isCorrect = (Boolean) choiceAttempt.getTag();
            if (isCorrect) {
                onCorrectAttempt();
            } else {
                onFailedAttempt();
            }
        }
    }
}