package ar.edu.uade.scrumgame.presentation.view.fragment.level;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import java.util.List;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Level1SubLevel1Game1Fragment extends GameFragment implements GameContentView {

    private static final String BUNDLE_EXTRA_PARAM_INFO_GAME = "ar.edu.uade.scrumgame.BUNDLE_EXTRA_PARAM_INFO_GAME";
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
    private String gameCode;
    private RadioButton choiceAttempt;

    public Level1SubLevel1Game1Fragment() {
        this.setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(LevelComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_long_text_choices, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            this.loadGame();
        }
    }

    private void loadGame() {
        RadioButton[] choices = new RadioButton[]{choice1, choice2, choice3, choice4};
        InfoGameModel infoGameModel = getArguments().getParcelable(BUNDLE_EXTRA_PARAM_INFO_GAME);
        if (infoGameModel != null) {
            this.gameCode = infoGameModel.getCode();
            List<GameContentModel> gameContent = infoGameModel.getContent();
            if (gameContent == null || gameContent.size() != 4) {
                throw new IllegalArgumentException("Choices must be 4");
            }
            for (int index = 0; index < 4; index++) {
                RadioButton radioButton = choices[index];
                GameContentModel gameContentModel = gameContent.get(index);
                radioButton.setText(gameContentModel.getData());
                radioButton.setTag(gameContentModel.getCorrect());
            }
            this.choices1.setOnCheckedChangeListener((group, checkedId) -> saveCheckedButtonAndClearCheck(group, checkedId, choices2));
            this.choices2.setOnCheckedChangeListener((group, checkedId) -> saveCheckedButtonAndClearCheck(group, checkedId, choices1));
        }
    }

    private void saveCheckedButtonAndClearCheck(RadioGroup radioGroup, int checkedId, RadioGroup otherRadioGroup) {
        Level1SubLevel1Game1Fragment.this.choiceAttempt = radioGroup.findViewById(checkedId);
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
