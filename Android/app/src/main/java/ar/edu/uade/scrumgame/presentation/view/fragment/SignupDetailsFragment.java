package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.Nullable;

import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.SignUpListener;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.data.entity.LevelStatusConstants;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.UserModel;
import ar.edu.uade.scrumgame.presentation.presenter.SignupDetailsPresenter;
import ar.edu.uade.scrumgame.presentation.view.SignupDetailsView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupDetailsFragment extends BaseFragment implements SignupDetailsView {

    @Override
    public void returnToLogin() {
        signupDetailsListener.onSignupDetailsCompleted();
    }

    @Override
    public void enterMenu() {
        signupDetailsListener.onSignupDetailsFailed();
    }

    @Inject
    SignupDetailsPresenter signupDetailsPresenter;

    private SignUpListener signupDetailsListener;

    @Override
    public void onAttach(Activity activity) {
        this.getComponent(LevelComponent.class).inject(this);
        super.onAttach(activity);
        if (activity instanceof SignUpListener) {
            this.signupDetailsListener = (SignUpListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_signup_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.signupDetailsPresenter.setView(this);
    }

    @Override
    public void showLoading() {
        this.progressLayout.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.progressLayout.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        this.showToast(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.signupDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.signupDetailsPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.signupDetailsPresenter.destroy();
    }


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

    @BindView(R.id.rl_progress)
    FrameLayout progressLayout;

    @OnClick(R.id.btnDone)
    public void signupDoneClicked() {
        try {
            if (validateForm()) {
                FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
                UserModel newUser = new UserModel(
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
                ProgressModel initialProgress = buildInitialProgress();
                this.signupDetailsPresenter.onDoneClicked(newUser, initialProgress);
            } else {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(this.getActivity());
                builder.title(R.string.form_incomplete_alert_title);
                builder.content(R.string.form_incomplete_alert_message);
                builder.positiveText("OK");
                builder.show();
            }
        } catch (NumberFormatException nfe) {
            showError(getString(R.string.form_error_age_nan));
        } catch (Exception e) {
            showError(getString(R.string.unknown_error));
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

    private ProgressModel buildInitialProgress() {
        ProgressModel initialProgress = new ProgressModel();
        initialProgress.setLevelId(1);
        initialProgress.setBlocked(false);
        initialProgress.setStatus(LevelStatusConstants.NOT_STARTED);
        initialProgress.setTutorialCompleted(false);
        initialProgress.setSublevelID(0);
        initialProgress.setActualGame(0);
        return initialProgress;
    }
}
