package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.os.Handler;

import ar.edu.uade.scrumgame.R;

public class SplashScreen extends BaseActivity {
    private static final Integer SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(() -> SplashScreen.this.navigator.navigateToLogin(SplashScreen.this), SPLASH_TIME);
    }
}
