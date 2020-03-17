package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ar.edu.uade.scrumgame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.button)
    Button button;

    @BindView(R.id.login)
    Button loginButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            navigateToMenu(user);
        }
        //DEBUG
        Realm realm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.button)
    public void signupClicked() {
        navigateToSignup();
    }

    public void navigateToMenu(FirebaseUser user) {
        this.navigator.navigateToMenu(this);
    }

    public void navigateToSignup() {
        this.navigator.navigateToSignup(this);
    }

    // TODO mandar a/mostrar form de login. proceso:
    // https://firebase.google.com/docs/auth/android/password-auth
    @OnClick(R.id.login)
    public void loginPressed() {
        String email = "gcaroniuade@gmail.com";
        String password = "123456";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LOGIN", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            navigateToMenu(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
