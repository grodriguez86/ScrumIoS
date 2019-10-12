package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.widget.Button;

import ar.edu.uade.scrumgame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void navigateToMenu() {
        this.navigator.navigateToMenu(this);
    }
}
