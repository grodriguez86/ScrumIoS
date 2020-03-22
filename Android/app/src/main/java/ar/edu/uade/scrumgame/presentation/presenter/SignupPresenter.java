package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.view.SignupView;

@PerActivity
public class SignupPresenter implements Presenter {

    private SignupView signupView;

    @Inject
    SignupPresenter() {
    }

    public void setView(@NonNull SignupView view) {
        this.signupView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.signupView = null;
    }

    public void initialize() {

    }

    public void onSignupDetailsClicked(String mail, String password) {
        this.signupView.signupUser(mail, password);
    }

    private void showViewLoading() {
        this.signupView.showLoading();
    }

    private void hideViewLoading() {
        this.signupView.hideLoading();
    }

    private void showViewRetry() {
        this.signupView.showRetry();
    }

    private void hideViewRetry() {
        this.signupView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.signupView.context(),
                errorBundle.getException());
        this.signupView.showError(errorMessage);
    }

}