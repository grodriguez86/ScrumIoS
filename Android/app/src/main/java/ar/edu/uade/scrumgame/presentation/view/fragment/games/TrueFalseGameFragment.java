package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.graphics.Color;
import android.widget.Button;

import java.util.List;

import androidx.core.content.ContextCompat;
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
    private Button[] choices;
    private Button choiceAttempt;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_choice_true_false;
    }

    @Override
    protected void doLoadGame() {
        this.choices = new Button[]{choice1Button, choice2Button};
        List<GameContentModel> gameContent = infoGameModel.getContent();
        if (gameContent.size() != NUMBER_OF_CHOICES) {
            throw new IllegalArgumentException("True/false choices must be two");
        }
        for (int index = 0; index < NUMBER_OF_CHOICES; index++) {
            this.choices[index].setText(gameContent.get(index).getData());
            this.choices[index].setTag(gameContent.get(index).getCorrect());
            this.choices[index].setOnClickListener(v -> {
                TrueFalseGameFragment.this.unselectChoice();
                v.setSelected(true);
                ((Button) v).setTextColor(Color.WHITE);
                this.choiceAttempt = (Button) v;
            });
        }
    }

    @Override
    protected void doLoadCompletedGame() {
        for (int index = 0; index < NUMBER_OF_CHOICES; index++) {
            Button button = this.choices[index];
            button.setEnabled(false);
            if (button.getTag().equals(true)) {
                button.performClick();
            }
        }
    }

    private void unselectChoice() {
        if (this.choices != null && this.choiceAttempt != null) {
            for (Button button : this.choices) {
                if (button.getTag().equals(this.choiceAttempt.getTag())) {
                    button.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.violet));
                    button.setSelected(false);
                }
            }
        }
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
