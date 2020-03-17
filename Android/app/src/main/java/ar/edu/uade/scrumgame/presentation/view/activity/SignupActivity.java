package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import ar.edu.uade.scrumgame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends BaseActivity {

    @BindView(R.id.bt_exit)
    ImageButton exitButton;

    @BindView(R.id.btnDone)
    Button continueButton;

    @BindView(R.id.inputEmail)
    EditText inputEmail;

    @BindView(R.id.inputPassword)
    EditText inputPassword;

    private FirebaseAuth mAuth;

    private static final String LOG_TAG = "SIGNUP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btnDone)
    public void signupClicked() {
        mAuth.signOut();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(LOG_TAG, "createUserWithEmail:success");
                                navigateToSignupDetails();
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
    }

    @OnClick(R.id.bt_exit)
    public void backPressed() {
        this.onBackPressed();
    }

    public void navigateToSignupDetails() {
        this.navigator.navigateToSignupDetails(this);
    }

}
