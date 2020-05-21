package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.adapter.ImageQuizAdapter;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import butterknife.BindView;

public class ImageQuizGameFragment extends GameFragment implements GameContentView, ImageQuizAdapter.OnItemClickListener {
    @BindView(R.id.picture_rv)
    RecyclerView picturesRecyclerView;
    private Boolean isChoiceCorrect = false;
    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_image_quiz;
    }

    @Override
    protected void doLoadGame() {
        this.setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        ImageQuizAdapter imageQuizAdapter = new ImageQuizAdapter(getActivity());
        imageQuizAdapter.setGameContentModels(infoGameModel.getContent());
        imageQuizAdapter.setOnItemClickListener(this);
        this.picturesRecyclerView.setAdapter(imageQuizAdapter);
        this.picturesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
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
    public void onImageSelected(GameContentModel gameContentModel) {
        isChoiceCorrect = gameContentModel.getCorrect();
    }
}