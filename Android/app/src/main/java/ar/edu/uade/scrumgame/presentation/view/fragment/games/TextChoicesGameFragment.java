package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.CompoundButtonCompat;
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
            radioButton.setTypeface(ResourcesCompat.getFont(this.getActivity(), R.font.gotham_rounded_medium));
            ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{-android.R.attr.state_checked}, new int[]{android.R.attr.state_checked}}, new int[]{Color.DKGRAY, Color.BLACK});
            CompoundButtonCompat.setButtonTintList(radioButton, colorStateList);
            radioButton.setTag(choice.getCorrect());
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            choices.addView(radioButton);
        }
        choices.setOnCheckedChangeListener((group, checkedId) -> TextChoicesGameFragment.this.choiceAttempt = choices.findViewById(checkedId));
    }

    @Override
    protected void doLoadCompletedGame() {
        int choicesCount = this.choices.getChildCount();
        for(int index = 0; index < choicesCount; index++){
            RadioButton button = (RadioButton) this.choices.getChildAt(index);
            button.setEnabled(false);
            if(button.getTag().equals(true)){
                button.setChecked(true);
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
