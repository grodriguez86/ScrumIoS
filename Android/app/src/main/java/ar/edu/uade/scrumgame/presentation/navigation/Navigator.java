package ar.edu.uade.scrumgame.presentation.navigation;

import android.content.Context;
import android.content.Intent;


import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.presentation.view.activity.BaseActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.InfoGameActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.InfoTheoryActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.LevelActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.LoginActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.MenuActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.SignupActivity;
import ar.edu.uade.scrumgame.presentation.view.activity.SignupDetailsActivity;
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

    public void navigateToLevelAfterGameFinished(InfoGameActivity infoGameActivity, Integer code) {
        Intent intent = new Intent(infoGameActivity, LevelActivity.class);
        intent.putExtra("levelCode", code);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        infoGameActivity.startActivity(intent);
    }

    public void navigateToInfoTheory(LevelActivity levelActivity, Integer levelCode, String levelName,
                                     String subLevelCode, Integer currentGame) {
        Intent intent = new Intent(levelActivity, InfoTheoryActivity.class);
        intent.putExtra("levelCode", levelCode);
        intent.putExtra("levelName", levelName);
        intent.putExtra("subLevelCode", subLevelCode);
        intent.putExtra("currentGame", currentGame);
        levelActivity.startActivity(intent);
    }

    public void navigateToPlaySubLevel(BaseActivity baseActivity, Integer levelCode,
                                       String levelTitle, String subLevelCode, String subLevelTitle,
                                       Integer currentGame) {
        if (baseActivity != null) {
            Intent intent = InfoGameActivity.getCallingIntent(baseActivity, levelCode,
                    levelTitle, subLevelCode, subLevelTitle, currentGame);
            baseActivity.startActivity(intent);
        }
    }

    public void navigateToLogin(BaseActivity baseActivity) {
        if (baseActivity != null) {
            Intent intent = new Intent(baseActivity.getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            baseActivity.startActivity(intent);
            baseActivity.finish();
        }
    }

    public void navigateToSignup(LoginActivity loginActivity) {
        if (loginActivity != null) {
            Intent intent = new Intent(loginActivity.getApplicationContext(), SignupActivity.class);
            loginActivity.startActivity(intent);
        }
    }

    public void navigateToSignupDetails(BaseActivity baseActivity) {
        if (baseActivity != null) {
            Intent intent = new Intent(baseActivity.getApplicationContext(), SignupDetailsActivity.class);
            baseActivity.startActivity(intent);
        }
    }
}
