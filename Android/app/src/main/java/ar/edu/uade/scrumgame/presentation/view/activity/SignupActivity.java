package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

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
        navigateToSignupDetails();
        if (true)
            return;
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGNUP", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            navigateToSignupDetails();
                        } else {
                            // If sign in fails, display a message to the user.
                            // com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.
                            Log.w("SIGNUP", "createUserWithEmail:failure", task.getException());
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException emailInUse) {
                                Toast.makeText(SignupActivity.this, "Ya existe un usuario con ese mail",
                                        Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(SignupActivity.this, "Ocurrió un error",
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
