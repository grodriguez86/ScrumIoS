package ar.edu.uade.scrumgame.presentation.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import ar.edu.uade.scrumgame.BuildConfig;
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
    @BindView(R.id.app_version_tv)
    AppCompatTextView appVersion;
    private LoginListener loginListener;

    @Override
    public void onAttach(Activity activity) {
        this.getComponent(LevelComponent.class).inject(this);
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
        this.appVersion.setText(String.format(getString(R.string.version), BuildConfig.VERSION_NAME));
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

    @BindView(R.id.resetPasswordTv)
    TextView resetPassword;

    @BindView(R.id.rl_progress)
    FrameLayout progressLayout;

    private void createResetPasswordAlert() {
        EditText input = new EditText(this.getActivity());
        input.setTypeface(ResourcesCompat.getFont(this.getActivity(), R.font.gotham_rounded_light));
        input.setHint(R.string.reset_password_input_hint);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        LinearLayout container = new LinearLayout(this.getActivity());
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.setMargins(30, 10, 30, 10);
        container.addView(input, linearLayoutParams);
        AlertDialog alert = new AlertDialog.Builder(this.getActivity())
                .setTitle(R.string.reset_password_alert)
                .setView(container)
                .setPositiveButton(R.string.accept, null)
                .show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            String email = input.getText().toString();
            if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                LoginFragment.this.loginPresenter.resetPassword(email);
                alert.dismiss();
            } else {
                input.setError(LoginFragment.this.getString(R.string.form_invalid_email));
            }
        });
    }

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
    public void showResetPasswordSuccessAlert(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage(String.format(this.getString(R.string.reset_password_success), email));
        builder.show();
    }

    @OnClick(R.id.resetPasswordTv)
    public void resetPassword() {
        this.createResetPasswordAlert();
    }

    @Override
    public void loggedIn() {
        this.loginListener.onLogin();
    }


}
