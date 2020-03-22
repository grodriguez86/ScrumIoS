package ar.edu.uade.scrumgame.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.HasComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerLevelComponent;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.view.fragment.SignupFragment;

public class SignupActivity extends BaseActivity implements HasComponent<LevelComponent>,SignupFragment.SignupListener {

    private static final String LOG_TAG = "SIGNUP";

    private LevelComponent levelComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SignupActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_signup);
        this.initializeInjector();
        SignupFragment signupFragment = new SignupFragment();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, signupFragment);
        }
    }

    @Override
    public void onSignupClicked(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        this.navigator.navigateToSignupDetails(this);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(LOG_TAG, "createUserWithEmail:success");
                        this.navigator.navigateToSignupDetails(this);
                    } else {
                        Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                        try {
                            if (task.getException() != null)
                                throw task.getException();
                        } catch (FirebaseAuthUserCollisionException emailInUse) {
                            Toast.makeText(SignupActivity.this, R.string.error_email_already_in_use,
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(SignupActivity.this, R.string.unknown_error,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
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
}
