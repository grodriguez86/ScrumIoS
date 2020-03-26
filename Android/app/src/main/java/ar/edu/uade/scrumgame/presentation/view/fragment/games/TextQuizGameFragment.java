package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import androidx.core.content.ContextCompat;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import butterknife.BindView;

public class TextQuizGameFragment extends GameFragment implements GameContentView {
    private static final int REQUIRED_CHOICES = 4;
    @BindView(R.id.rg_choices_1)
    RadioGroup choices1;
    @BindView(R.id.rg_choices_2)
    RadioGroup choices2;
    @BindView(R.id.rb_choice_1)
    RadioButton choice1;
    @BindView(R.id.rb_choice_2)
    RadioButton choice2;
    @BindView(R.id.rb_choice_3)
    RadioButton choice3;
    @BindView(R.id.rb_choice_4)
    RadioButton choice4;
    private RadioButton choiceAttempt;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_long_text_choices;
    }

    @Override
    protected void doLoadGame() {
        RadioButton[] choices = new RadioButton[]{choice1, choice2, choice3, choice4};
        List<GameContentModel> gameContent = infoGameModel.getContent();
        if (gameContent == null || gameContent.size() != REQUIRED_CHOICES) {
            throw new IllegalArgumentException("Choices must be 4");
        }
        for (int index = 0; index < REQUIRED_CHOICES; index++) {
            RadioButton radioButton = choices[index];
            GameContentModel gameContentModel = gameContent.get(index);
            radioButton.setText(gameContentModel.getData());
            radioButton.setTag(gameContentModel.getCorrect());
        }
        this.choices1.setOnCheckedChangeListener((group, checkedId) -> saveCheckedButtonAndClearCheck(group, checkedId, choices2));
        this.choices2.setOnCheckedChangeListener((group, checkedId) -> saveCheckedButtonAndClearCheck(group, checkedId, choices1));
    }

    private void saveCheckedButtonAndClearCheck(RadioGroup radioGroup, int checkedId, RadioGroup otherRadioGroup) {
        if (this.choiceAttempt != null) {
            this.choiceAttempt.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.violet));
        }
        this.choiceAttempt = radioGroup.findViewById(checkedId);
        this.choiceAttempt.setTextColor(Color.WHITE);
        if (otherRadioGroup.getCheckedRadioButtonId() != -1) {
            otherRadioGroup.setOnCheckedChangeListener(null);
            otherRadioGroup.clearCheck();
            otherRadioGroup.setOnCheckedChangeListener((group, newCheckedId) -> saveCheckedButtonAndClearCheck(group, newCheckedId, radioGroup));
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