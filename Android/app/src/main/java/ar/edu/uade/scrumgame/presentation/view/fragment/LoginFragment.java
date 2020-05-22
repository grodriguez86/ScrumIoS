package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RelativeLayout;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.di.components.LevelComponent;
import ar.edu.uade.scrumgame.presentation.presenter.LoginPresenter;
import ar.edu.uade.scrumgame.presentation.view.LoginView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginView {

    public interface LoginListener {
        void onLogin();

        void onSignupPressed();

        void onGoToSignupDetails();
    }

    @Inject
    LoginPresenter loginPresenter;

    private LoginListener loginListener;

    public LoginFragment() {
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
        if (activity instanceof LoginFragment.LoginListener) {
            this.loginListener = (LoginFragment.LoginListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.loginPresenter.setView(this);
        this.loginPresenter.initialize();
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
        this.loginPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.loginPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.loginPresenter.destroy();
    }

    @BindView(R.id.login)
    Button loginButton;

    @BindView(R.id.inputEmail)
    EditText inputEmail;

    @BindView(R.id.inputPassword)
    EditText inputPassword;

    @BindView(R.id.rl_progress)
    RelativeLayout progressLayout;

    @OnClick(R.id.login)
    public void login() {
        if (loginPresenter != null) {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                this.loginPresenter.onLoginClicked(email, password);
            }
        }
    }

    @Override
    public void navigateToSignupDetails() {
        this.loginListener.onGoToSignupDetails();
    }

    @Override
    @OnClick(R.id.signup)
    public void goToSignup() {
        this.loginListener.onSignupPressed();
    }

    @Override
    public void loggedIn() {
        this.loginListener.onLogin();
    }


}
