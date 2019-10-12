package ar.edu.uade.scrumgame.presentation.navigation;

import android.content.Context;
import android.content.Intent;


import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.presentation.view.activity.LoginActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.MenuActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.SplashScreen;

@Singleton
public class Navigator {

    @Inject
    Navigator() {
    }

    public void navigateToMenu(Context context) {
        if (context != null) {
            Intent intentToLaunch = MenuActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToLevel(MenuActivity menuActivity, Integer code) {
    }

    public void navigateToLogin(SplashScreen splashScreen) {
        if (splashScreen != null) {
            Intent intent = new Intent(splashScreen.getApplicationContext(), LoginActivity.class);
            splashScreen.startActivity(intent);
            splashScreen.finish();
        }
    }
}
