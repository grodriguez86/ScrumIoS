package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.UserCredentials;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.*;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.models.UserModel;
import ar.edu.uade.scrumgame.presentation.view.LoginView;

@PerActivity
public class LoginPresenter implements Presenter {

    private LoginView loginView;
    private GetLoggedInUser getLoggedInUserUseCase;
    private GetProgressListRemotely getProgressListRemotelyUseCase;
    private GetUserRemotely getUserRemotelyUseCase;
    private Login loginUseCase;
    private SaveUserLocally saveUserLocallyUseCase;
    private UserDataMapper userDataMapper;
    private ResetPassword resetPasswordUseCase;
    private String email;

    @Inject
    public LoginPresenter(GetLoggedInUser getLoggedInUserUseCase,
                          GetProgressListRemotely getProgressListRemotelyUseCase,
                          GetUserRemotely getUserRemotelyUseCase,
                          Login loginUseCase,
                          SaveUserLocally saveUserLocallyUseCase,
                          ResetPassword resetPasswordUseCase,
                          UserDataMapper userDataMapper
    ) {
        this.getLoggedInUserUseCase = getLoggedInUserUseCase;
        this.getProgressListRemotelyUseCase = getProgressListRemotelyUseCase;
        this.getUserRemotelyUseCase = getUserRemotelyUseCase;
        this.loginUseCase = loginUseCase;
        this.saveUserLocallyUseCase = saveUserLocallyUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
        this.userDataMapper = userDataMapper;
    }

    public void setView(@NonNull LoginView view) {
        this.loginView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.loginView = null;
        this.getLoggedInUserUseCase.dispose();
        this.getProgressListRemotelyUseCase.dispose();
        this.getUserRemotelyUseCase.dispose();
        this.saveUserLocallyUseCase.dispose();
        this.resetPasswordUseCase.dispose();
    }

    public void initialize() {
        this.showViewLoading();
        this.getLoggedInUserUseCase.execute(new GetLoggedInUserObserver(), true);
    }

    private void loggedInUser(User loggedInUser) {
        if (loggedInUser != null) {
            this.loginView.loggedIn();
        }
    }

    public void onLoginClicked(String mail, String password) {
        showViewLoading();
        loginUseCase.execute(new DefaultObserver<String>() {
            @Override
            public void onNext(String email) {
                getUserRemotelyUseCase.execute(new DefaultObserver<User>() {

                    @Override
                    public void onNext(User user) {
                        UserModel userModel = userDataMapper.userToUserModel(user);
                        if (userModel.isNull()) { // User data was never saved to firebase
                            // TODO CHECK IF LOCAL DATA IS AVAILABLE FOR THIS USER, IF SO TRY TO SAVE IT.

                            // TODO ELSE REDIRECT TO SIGNUP DETAILS
                            hideViewLoading();
                            loginView.navigateToSignupDetails();
                        } else {
                            getProgressListRemotelyUseCase.execute(new DefaultObserver<List<Progress>>() {
                                @Override
                                public void onComplete() {
                                    saveUserLocallyUseCase.execute(new DefaultObserver<Void>() {
                                        @Override
                                        public void onComplete() {
                                            hideViewLoading();
                                            loginView.loggedIn();
                                        }

                                        @Override
                                        public void onError(Throwable exception) {
                                            // Failed to save user to Realm... continue anyway
                                            hideViewLoading();
                                            loginView.loggedIn();
                                            showErrorMessage(new DefaultErrorBundle((Exception) exception));
                                        }
                                    }, user);

                                }

                                @Override
                                public void onError(Throwable exception) {
                                    hideViewLoading();
                                    showErrorMessage(new DefaultErrorBundle((Exception) exception));
                                }
                            }, null);
                        }
                    }

                    @Override
                    public void onError(Throwable exception) {
                        hideViewLoading();
                        showErrorMessage(new DefaultErrorBundle((Exception) exception));
                    }
                }, email);
            }

            @Override
            public void onError(Throwable exception) {
                hideViewLoading();
                showErrorMessage(new DefaultErrorBundle((Exception) exception));
            }
        }, new UserCredentials(mail, password));
    }

    private void showViewLoading() {
        this.loginView.showLoading();
    }

    private void hideViewLoading() {
        this.loginView.hideLoading();
    }

    private void showViewRetry() {
        this.loginView.showRetry();
    }

    private void hideViewRetry() {
        this.loginView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.loginView.context(),
                errorBundle.getException());
        this.loginView.showError(errorMessage);
    }

    public void resetPassword(String email) {
        this.email = email;
        this.resetPasswordUseCase.execute(new ResetPasswordObserver(), email);
        this.showViewLoading();
    }

    private void showResetPasswordSuccessAlert() {
        this.loginView.showResetPasswordSuccessAlert(this.email);
    }

    private void showResetPasswordErrorAlert(String error) {
        this.loginView.showError(error);
    }

    private final class GetLoggedInUserObserver extends DefaultObserver<User> {

        @Override
        public void onNext(User loggedInUser) {
            LoginPresenter.this.hideViewLoading();
            LoginPresenter.this.loggedInUser(loggedInUser);
        }

        @Override
        public void onError(Throwable exception) {
            LoginPresenter.this.hideViewLoading();
        }
    }

    private final class ResetPasswordObserver extends DefaultObserver<Void> {

        @Override
        public void onComplete() {
            LoginPresenter.this.hideViewLoading();
            LoginPresenter.this.showResetPasswordSuccessAlert();
        }

        @Override
        public void onError(Throwable exception) {
            LoginPresenter.this.hideViewLoading();
            LoginPresenter.this.showResetPasswordErrorAlert(exception.getMessage());
        }
    }

}