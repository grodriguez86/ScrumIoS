package ar.edu.uade.scrumgame.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.view.fragment.LoginFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.MenuFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class LoginActivity extends BaseActivity implements HasComponent<LevelComponent>, LoginFragment.LoginListener {

    private LevelComponent levelComponent;

    private FirebaseAuth mAuth;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MenuActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        this.setContentView(R.layout.activity_login);
        this.initializeInjector();
        mAuth = FirebaseAuth.getInstance();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new LoginFragment());
        }
    }

    private void initializeInjector() {
        this.levelComponent = DaggerLevelComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }


    @Override
    public LevelComponent getComponent() {
        return levelComponent;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            navigateToMenu(user);
        }
    }

    public void navigateToMenu(FirebaseUser user) {
        this.navigator.navigateToMenu(this);
    }

    public void navigateToSignup() {
        this.navigator.navigateToSignup(this);
    }

    @Override
    public void onLogin() {
        this.navigator.navigateToMenu(this);
    }

    @Override
    public void onSignupPressed() {
        this.navigator.navigateToSignup(this);
    }

    @Override
    public void onGoToSignupDetails() {
        this.navigator.navigateToSignupDetails(this);
    }
}
