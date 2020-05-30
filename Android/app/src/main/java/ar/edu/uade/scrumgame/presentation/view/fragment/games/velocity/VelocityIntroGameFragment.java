package ar.edu.uade.scrumgame.presentation.view.fragment.games.velocity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.velocity.UnityPlayerActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VelocityIntroGameFragment extends Fragment {
    public interface OnVelocityGameFinishedListener {
        void onGameFinished(int expectedScore, int score);
    }

    private static int ON_GAME_COMPLETED_CODE = 90;
    @BindView(R.id.expectedScoreTv)
    EditText expectedScore;
    @BindView(R.id.playBtn)
    Button playButton;
    private OnVelocityGameFinishedListener onVelocityGameFinishedListener;


    public static VelocityIntroGameFragment newInstance() {
        return new VelocityIntroGameFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnVelocityGameFinishedListener) {
            this.onVelocityGameFinishedListener = (OnVelocityGameFinishedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onVelocityGameFinishedListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_velocity_intro, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.playBtn)
    public void playGame() {
        if (!expectedScore.getText().toString().isEmpty()) {
            Intent intent = new Intent(this.getActivity(), UnityPlayerActivity.class);
            intent.putExtra("expectedScore", Integer.valueOf(expectedScore.getText().toString()));
            startActivityForResult(intent, ON_GAME_COMPLETED_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ON_GAME_COMPLETED_CODE && data != null && this.onVelocityGameFinishedListener != null) {
            this.onVelocityGameFinishedListener.onGameFinished(data.getIntExtra("expectedScore", 0), data.getIntExtra("currentScore", 0));
        }
    }
}