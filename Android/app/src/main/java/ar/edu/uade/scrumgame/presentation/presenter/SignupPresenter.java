package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

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
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.presenter.observers.SaveUserLocalAndRemoteObserver;
import ar.edu.uade.scrumgame.presentation.view.SignupView;

import java.util.Collections;

@PerActivity
public class SignupPresenter implements SignUpBasePresenter {

    private SignupView signupView;
    private SignUp signUpUseCase;
    private GetUserLocally getUserLocallyUseCase;
    private GetUserRemotely getUserRemotelyUseCase;
    private SaveUserLocalAndRemote saveUserLocalAndRemoteUseCase;
    private SaveProgressList saveProgressListUseCase;
    private SaveUserOverallData saveUserOverallDataUseCase;
    private GetInitialProgress getInitialProgressUseCase;
    private CheckIncompleteSignUp checkIncompleteSignUpUseCase;
    private UserDataMapper userDataMapper;
    private UserCredentials userCredentials;

    @Inject
    SignupPresenter(SignUp signUpUseCase, GetUserLocally getUserLocallyUseCase, GetUserRemotely getUserRemotelyUseCase,
                    SaveUserLocalAndRemote saveUserLocalAndRemoteUseCase, SaveProgressList saveProgressListUseCase,
                    SaveUserOverallData saveUserOverallDataUseCase, GetInitialProgress getInitialProgressUseCase,
                    CheckIncompleteSignUp checkIncompleteSignUpUseCase,
                    UserDataMapper userDataMapper) {
        this.signUpUseCase = signUpUseCase;
        this.getUserLocallyUseCase = getUserLocallyUseCase;
        this.getUserRemotelyUseCase = getUserRemotelyUseCase;
        this.saveUserLocalAndRemoteUseCase = saveUserLocalAndRemoteUseCase;
        this.saveProgressListUseCase = saveProgressListUseCase;
        this.saveUserOverallDataUseCase = saveUserOverallDataUseCase;
        this.getInitialProgressUseCase = getInitialProgressUseCase;
        this.checkIncompleteSignUpUseCase = checkIncompleteSignUpUseCase;
        this.userDataMapper = userDataMapper;
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
        this.saveUserOverallDataUseCase.dispose();
        this.saveProgressListUseCase.dispose();
        this.saveUserLocalAndRemoteUseCase.dispose();
        this.getUserRemotelyUseCase.dispose();
        this.getUserLocallyUseCase.dispose();
        this.getInitialProgressUseCase.dispose();
        this.checkIncompleteSignUpUseCase.dispose();
    }

    public void initialize() {

    }

    public void onSignUpClicked(String mail, String password) {
        this.showViewLoading();
        this.userCredentials = new UserCredentials(mail, password);
        this.isUserRegistered();
    }

    private void showViewLoading() {
        this.signupView.showLoading();
    }

    @Override
    public void hideViewLoading() {
        this.signupView.hideLoading();
    }

    @Override
    public void showViewRetry() {
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

    private void isUserRegistered() {
        if (this.userCredentials != null) {
            String email = this.userCredentials.getEmail();
            this.checkIncompleteSignUpUseCase.execute(new IncompleteSignUpObserver(), email);
        }
    }


    private void retrySignUp() {
        if (this.userCredentials != null) {
            String email = this.userCredentials.getEmail();
            this.getUserLocallyUseCase.execute(new LocalUserObserver(), email);
        }
    }

    private void retryFirebaseSignUp(User user) {
        this.getInitialProgressUseCase.execute(new GetInitialProgressObserver(user), null);
    }


    private void continueSignUp() {
        if (this.userCredentials != null) {
            this.signUpUseCase.execute(new SignUpObserver(), this.userCredentials);
        }
    }


    private void onSignUpVerificationSuccess() {
        if (this.userCredentials != null) {
            this.signupView.signupUser(this.userCredentials.getEmail(), this.userCredentials.getPassword());
        }
    }

    private final class LocalUserObserver extends DefaultObserver<User> {
        @Override
        public void onNext(User localUser) {
            SignupPresenter.this.retryFirebaseSignUp(localUser);
        }
    }

    private final class SignUpObserver extends DefaultObserver<Void> {
        @Override
        public void onComplete() {
            SignupPresenter.this.hideViewLoading();
            SignupPresenter.this.onSignUpVerificationSuccess();
        }

        @Override
        public void onError(Throwable exception) {
            SignupPresenter.this.hideViewLoading();
            SignupPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) exception));
            SignupPresenter.this.showViewRetry();
        }
    }

    private final class GetInitialProgressObserver extends DefaultObserver<Progress> {
        private User user;

        public GetInitialProgressObserver(User user) {
            this.user = user;
        }

        @Override
        public void onNext(Progress progress) {
            ProgressModel initialProgress = SignupPresenter.this.userDataMapper.progressToProgressModel(progress);
            SignupPresenter.this.saveUserLocalAndRemoteUseCase.execute(new SaveUserLocalAndRemoteObserver(saveUserOverallDataUseCase, saveProgressListUseCase, SignupPresenter.this, signupView, userDataMapper, Collections.singletonList(initialProgress)), user);
        }
    }

    private final class IncompleteSignUpObserver extends DefaultObserver<String> {
        @Override
        public void onNext(String status) {
            SignupPresenter.this.hideViewLoading();
            switch (status) {
                case CheckIncompleteSignUp.RETRY_SIGN_UP:
                    SignupPresenter.this.retrySignUp();
                    break;
                case CheckIncompleteSignUp.CONTINUE_SIGN_UP:
                    SignupPresenter.this.continueSignUp();
                    break;
            }
        }


        @Override
        public void onError(Throwable exception) {
            SignupPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) exception));
            SignupPresenter.this.hideViewLoading();
            SignupPresenter.this.showViewRetry();
        }
    }



}