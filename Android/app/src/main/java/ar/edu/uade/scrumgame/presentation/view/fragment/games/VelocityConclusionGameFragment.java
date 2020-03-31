package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ar.edu.uade.scrumgame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VelocityConclusionGameFragment extends Fragment {
    public interface OnVelocityConclusionFinishedListener {
        void onVelocityGameEnded();
    }

    @BindView(R.id.finishBtn)
    Button finishButton;
    private OnVelocityConclusionFinishedListener onVelocityConclusionFinishedListener;


    public static VelocityConclusionGameFragment newInstance() {
        return new VelocityConclusionGameFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnVelocityConclusionFinishedListener) {
            this.onVelocityConclusionFinishedListener = (OnVelocityConclusionFinishedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onVelocityConclusionFinishedListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_velocity_conclusion, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.finishBtn)
    public void onFinishButtonClicked() {
        if (this.onVelocityConclusionFinishedListener != null) {
            this.onVelocityConclusionFinishedListener.onVelocityGameEnded();
        }
    }
}