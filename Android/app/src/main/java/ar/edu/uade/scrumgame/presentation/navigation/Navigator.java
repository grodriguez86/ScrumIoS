package ar.edu.uade.scrumgame.presentation.navigation;

import android.content.Context;
import android.content.Intent;


import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.view.activity.InfoTheoryActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.LevelActivity;
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
        Intent intent = new Intent(menuActivity, LevelActivity.class);
        intent.putExtra("levelCode", code);
        menuActivity.startActivity(intent);
    }

    public void navigateToInfoTheory(LevelActivity levelActivity, Integer levelCode, String levelName, String subLevelCode) {
        Intent intent = new Intent(levelActivity, InfoTheoryActivity.class);
        intent.putExtra("levelCode", levelCode);
        intent.putExtra("levelName", levelName);
        intent.putExtra("subLevelCode", subLevelCode);
        levelActivity.startActivity(intent);
    }

    public void navigateToPlaySubLevel(InfoTheoryActivity infoTheoryActivity, Integer levelCode, String subLevelCode) {
        // TODO
        System.out.println("TODO: implementar navegacion a JUGAR subnivel");
    }

    public void navigateToLogin(SplashScreen splashScreen) {
        if (splashScreen != null) {
            Intent intent = new Intent(splashScreen.getApplicationContext(), LoginActivity.class);
            splashScreen.startActivity(intent);
            splashScreen.finish();
        }
    }
}
