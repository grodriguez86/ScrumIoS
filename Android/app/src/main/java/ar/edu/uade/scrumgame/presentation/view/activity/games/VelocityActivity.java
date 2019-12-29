package ar.edu.uade.scrumgame.presentation.view.activity.games;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.activity.BaseActivity;
import ar.edu.uade.scrumgame.velocity.UnityPlayerActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VelocityActivity extends BaseActivity {
    private static int ON_GAME_COMPLETED_CODE = 90;
    @BindView(R.id.expectedScoreTv)
    TextView expectedScore;
    @BindView(R.id.playBtn)
    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS | Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_game_velocity);
        ButterKnife.bind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ON_GAME_COMPLETED_CODE) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("currentScore", data.getIntExtra("currentScore", 0));
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    @OnClick(R.id.playBtn)
    public void playGame() {
        if (!expectedScore.getText().toString().isEmpty()) {
            Intent intent = new Intent(this, UnityPlayerActivity.class);
            intent.putExtra("expectedScore", Integer.valueOf(expectedScore.getText().toString()));
            startActivityForResult(intent, ON_GAME_COMPLETED_CODE);
        }
    }
}
