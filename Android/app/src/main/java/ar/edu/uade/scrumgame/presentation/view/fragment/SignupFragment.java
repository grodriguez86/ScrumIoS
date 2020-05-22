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

import android.widget.RelativeLayout;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.presenter.SignupPresenter;
import ar.edu.uade.scrumgame.presentation.view.SignupView;
import ar.edu.uade.scrumgame.presentation.view.fragment.listeners.SignUpStep1Listener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupFragment extends BaseFragment implements SignupView {
    private static final Integer MINIMUM_PASSWORD_LENGTH = 6;

    @Override
    public void signupUser(String email, String password) {
        if (this.signupListener != null) {
            this.signupListener.onSignupClicked(email, password);
        }
    }

    @BindView(R.id.bt_exit)
    ImageButton exitButton;
    @BindView(R.id.btnDone)
    Button continueButton;
    @BindView(R.id.inputEmail)
    EditText inputEmail;
    @BindView(R.id.inputPassword)
    EditText inputPassword;
    @BindView(R.id.rl_progress)
    RelativeLayout progressLayout;
    @Inject
    SignupPresenter signupPresenter;

    private SignUpStep1Listener signupListener;

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
        if (activity instanceof SignUpStep1Listener) {
            this.signupListener = (SignUpStep1Listener) activity;
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

    @Override
    public void returnToLogin() {
        signupListener.onSignupDetailsCompleted();
    }

    @Override
    public void enterMenu() {
        signupListener.onSignupDetailsFailed();
    }

    @OnClick(R.id.btnDone)
    public void signupClicked() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (signupListener != null && this.validForm(email, password)) {
            this.signupPresenter.onSignUpClicked(email, password);
        }
    }

    public Boolean validForm(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password.length() >= MINIMUM_PASSWORD_LENGTH) {
                    return true;
                } else {
                    this.showError(getString(R.string.form_invalid_password_length));
                }
            } else {
                this.showError(getString(R.string.form_invalid_email));
            }
        } else {
            this.showError(getString(R.string.form_incomplete_alert_message));
        }
        return false;
    }

    @OnClick(R.id.bt_exit)
    public void backPressed() {
        if (this.getActivity() != null) {
            this.getActivity().onBackPressed();
        }
    }

}
