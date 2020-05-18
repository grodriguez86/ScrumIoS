package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.presenter.SignupPresenter;
import ar.edu.uade.scrumgame.presentation.view.SignupView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ar.edu.uade.scrumgame.presentation.constants.SignupConstant.MINIMUM_PASSWORD_LENGTH;

public class SignupFragment extends BaseFragment implements SignupView {

    public interface SignupListener {
        void onSignupClicked(String email, String password);
    }

    @Override
    public void signupUser(String email, String password) {
        this.signupPresenter.onSignupDetailsClicked(email, password);
    }

    @Inject
    SignupPresenter signupPresenter;

    private SignupListener signupListener;

    public SignupFragment() {
        this.setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(LevelComponent.class).inject(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SignupFragment.SignupListener) {
            this.signupListener = (SignupFragment.SignupListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.signupPresenter.setView(this);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        this.signupPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.signupPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.signupPresenter.destroy();
    }

    @BindView(R.id.bt_exit)
    ImageButton exitButton;

    @BindView(R.id.btnDone)
    Button continueButton;

    @BindView(R.id.inputEmail)
    EditText inputEmail;

    @BindView(R.id.inputPassword)
    EditText inputPassword;

    @OnClick(R.id.btnDone)
    public void signupClicked() {
        if (signupListener != null) {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            if (validateEmail(email) && validatePassword(password)) {
                this.signupListener.onSignupClicked(email, password);
            }
        }
    }

    private boolean validateEmail(String email) {
        boolean valid = !email.isEmpty() && EmailValidator.getInstance(false).isValid(email);
        if (!valid)
            this.showError(this.getActivity().getString(R.string.signup_form_invalid_email));
        return valid;
    }

    private boolean validatePassword(String password) {
        boolean valid = !password.isEmpty() && password.length() >= MINIMUM_PASSWORD_LENGTH;
        if (!valid)
            this.showError(this.getActivity().getString(R.string.signup_form_invalid_password));
        return valid;
    }

    @OnClick(R.id.bt_exit)
    public void backPressed() {
//        TODO this.onBackPressed();
    }

}
