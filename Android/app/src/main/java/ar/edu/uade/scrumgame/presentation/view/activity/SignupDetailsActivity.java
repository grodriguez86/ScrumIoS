package ar.edu.uade.scrumgame.presentation.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import ar.edu.uade.scrumgame.data.entity.UserEntity;
import ar.edu.uade.scrumgame.data.entity.UserOverallDataEntity;
import ar.edu.uade.scrumgame.data.entity.mapper.ProgressEntityMapper;
import ar.edu.uade.scrumgame.data.entity.mapper.UserEntityMapper;
import ar.edu.uade.scrumgame.domain.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class SignupDetailsActivity extends BaseActivity {

    @BindView(R.id.btBack)
    ImageButton backButton;

    @BindView(R.id.btnDone)
    Button doneButton;

    @BindView(R.id.inputName)
    EditText inputName;

    @BindView(R.id.inputAge)
    EditText inputAge;

    @BindView(R.id.inputSex)
    Spinner inputSex;

    @BindView(R.id.inputProfession)
    EditText inputProfession;

    @BindView(R.id.inputCity)
    EditText inputCity;

    @BindView(R.id.inputProvince)
    EditText inputProvince;

    @BindView(R.id.inputCountry)
    EditText inputCountry;

    @BindView(R.id.inputGamesTasteLevel)
    Spinner inputGamesTasteLevel;

    @BindView(R.id.inputGamesTime)
    Spinner inputGamesTime;

    private FirebaseUser loggedUser;

    @OnClick(R.id.btBack)
    public void goBack() {
        this.onBackPressed();
    }

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_details);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        loggedUser = mAuth.getCurrentUser();
        if (loggedUser == null) {
            Toast.makeText(this, "No se pudo encontrar el usuario", Toast.LENGTH_SHORT).show();
            this.navigator.navigateToLogin(this);
        }
    }

    @OnClick(R.id.btnDone)
    public void signupDoneClicked() {
        try {
            if (validateForm()) {
                User newUser = new User(
                        inputName.getText().toString(),
                        loggedUser.getEmail(),
                        Integer.parseInt(inputAge.getText().toString()),
                        inputProfession.getText().toString(),
                        loggedUser.getUid(),
                        inputCity.getText().toString(),
                        inputSex.getSelectedItem().toString(),
                        inputProvince.getText().toString(),
                        inputCountry.getText().toString(),
                        inputGamesTasteLevel.getSelectedItem().toString(),
                        inputGamesTime.getSelectedItem().toString());
                saveToRealm(newUser);
                createRemoteUserDocument(newUser);
            } else {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
                builder.title("Error");
                builder.content("Tenés campos incompletos!");
                builder.positiveText("OK");
                builder.show();
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "La edad debe ser un número entero", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Ocurrió un error desconocido", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToRealm(User user) {
        UserEntityMapper userEntityMapper = new UserEntityMapper();
        UserEntity newUserEntity = userEntityMapper.userToUserEntity(user);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(newUserEntity);
        realm.commitTransaction();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(new UserOverallDataEntity(1));
        realm.commitTransaction();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ProgressEntity.buildInitialProgress());
        realm.commitTransaction();
    }

    private void createRemoteUserDocument(User user) {
        try {
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("users")
                    .document(user.getMail())
                    .set(user)
                    .addOnSuccessListener(userSaveSuccess -> {
                        Log.d("SIGNUP", "User DocumentSnapshot written");
                        firebaseFirestore.collection(String.format("users/%s/levels", user.getMail()))
                                .document("level_1")
                                .set(new ProgressEntityMapper()
                                        .progressEntityToProgress(ProgressEntity.buildInitialProgress()))
                                .addOnSuccessListener(userProgressInitSuccess -> {
                                    Log.d("SIGNUP", "UserProgress DocumentSnapshot written");
                                    navigateToMenu();
                                })
                                .addOnFailureListener(e -> Log.w("SIGNUP", "Error adding progress document", e));
                        navigateToMenu();
                    })
                    .addOnFailureListener(e -> Log.w("SIGNUP", "Error adding user document", e));
        } catch (Exception e) {
            Toast.makeText(this, "Ocurrió un error creando el usuario remoto", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @return Returns whether the form is valid
     */
    public boolean validateForm() {
        if (TextUtils.isEmpty(inputName.getText().toString()))
            return false;
        if (TextUtils.isEmpty(inputAge.getText().toString()))
            return false;
        if (inputSex.getSelectedItem() == null)
            return false;
        if (TextUtils.isEmpty(inputProfession.getText().toString()))
            return false;
        if (TextUtils.isEmpty(inputCity.getText().toString()))
            return false;
        if (TextUtils.isEmpty(inputProvince.getText().toString()))
            return false;
        if (TextUtils.isEmpty(inputCountry.getText().toString()))
            return false;
        if (inputGamesTasteLevel.getSelectedItem() == null)
            return false;
        if (inputGamesTime.getSelectedItem() == null)
            return false;
        return true;
    }

    public void navigateToMenu() {
        this.navigator.navigateToMenu(this);
    }

}
