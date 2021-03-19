package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.GameContentView;
import ar.edu.uade.scrumgame.presentation.view.adapter.ImageQuizAdapter;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import ar.edu.uade.scrumgame.presentation.view.utils.GameModelUtils;
import butterknife.BindView;

public class ImageQuizGameFragment extends GameFragment implements GameContentView, ImageQuizAdapter.OnItemClickListener {
    @BindView(R.id.picture_rv)
    RecyclerView picturesRecyclerView;
    private ImageQuizAdapter imageQuizAdapter;
    private Boolean isChoiceCorrect = false;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_image_quiz;
    }

    @Override
    protected void doLoadGame() {
        this.setUpRecyclerView();
    }

    @Override
    protected void doLoadCompletedGame() {
        int correctOptionIndex = GameModelUtils.getCorrectOptionIndex(infoGameModel.getContent());
        if (this.picturesRecyclerView != null) {
            this.imageQuizAdapter.setEnabled(false);
            new Handler().postDelayed(() -> {
                RecyclerView.ViewHolder viewHolder = this.picturesRecyclerView.findViewHolderForAdapterPosition(correctOptionIndex);
                if (viewHolder != null) {
                    viewHolder.itemView.setSelected(true);
                }
            }, 100);
        }
    }

    private void setUpRecyclerView() {
        this.imageQuizAdapter = new ImageQuizAdapter(getActivity());
        this.imageQuizAdapter.setGameContentModels(infoGameModel.getContent());
        this.imageQuizAdapter.setOnItemClickListener(this);
        this.picturesRecyclerView.setAdapter(this.imageQuizAdapter);
        this.picturesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
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
