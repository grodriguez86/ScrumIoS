package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import ar.edu.uade.scrumgame.presentation.view.utils.ViewIdGenerator;
import butterknife.BindView;

public class TextChoicesGameFragment extends GameFragment implements GameContentView {
    @BindView(R.id.choices_rg)
    RadioGroup choices;
    private RadioButton choiceAttempt;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_text_choices_quiz;
    }

    @Override
    protected void doLoadGame() {
        List<GameContentModel> gameContent = infoGameModel.getContent();
        for (GameContentModel choice : gameContent) {
            RadioButton radioButton = new RadioButton(this.getActivity());
            radioButton.setId(ViewIdGenerator.generateViewId());
            radioButton.setText(choice.getData());
            radioButton.setTag(choice.getCorrect());
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            choices.addView(radioButton);
        }
        choices.setOnCheckedChangeListener((group, checkedId) -> TextChoicesGameFragment.this.choiceAttempt = choices.findViewById(checkedId));
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