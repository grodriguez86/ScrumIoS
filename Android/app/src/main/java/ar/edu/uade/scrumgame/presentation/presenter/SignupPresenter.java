package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.data.exception.LocalUserNotFoundException;
import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.UserCredentials;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.*;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.exception.UserAlreadyRegisteredException;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.presenter.observers.SaveUserLocalAndRemoteObserver;
import ar.edu.uade.scrumgame.presentation.view.SignupView;

@PerActivity
public class SignupPresenter implements SignUpBasePresenter {

    private SignupView signupView;
    private SignUp signUpUseCase;
    private GetUserLocally getUserLocallyUseCase;
    private GetUserRemotely getUserRemotelyUseCase;
    private SaveUserLocalAndRemote saveUserLocalAndRemoteUseCase;
    private SaveProgress saveProgressUseCase;
    private SaveUserOverallData saveUserOverallDataUseCase;
    private GetInitialProgress getInitialProgressUseCase;
    private UserDataMapper userDataMapper;
    private UserCredentials userCredentials;

    @Inject
    SignupPresenter(SignUp signUpUseCase, GetUserLocally getUserLocallyUseCase, GetUserRemotely getUserRemotelyUseCase,
                    SaveUserLocalAndRemote saveUserLocalAndRemoteUseCase, SaveProgress saveProgressUseCase,
                    SaveUserOverallData saveUserOverallDataUseCase, GetInitialProgress getInitialProgressUseCase,
                    UserDataMapper userDataMapper) {
        this.signUpUseCase = signUpUseCase;
        this.getUserLocallyUseCase = getUserLocallyUseCase;
        this.getUserRemotelyUseCase = getUserRemotelyUseCase;
        this.saveUserLocalAndRemoteUseCase = saveUserLocalAndRemoteUseCase;
        this.saveProgressUseCase = saveProgressUseCase;
        this.saveUserOverallDataUseCase = saveUserOverallDataUseCase;
        this.getInitialProgressUseCase= getInitialProgressUseCase;
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
        this.saveProgressUseCase.dispose();
        this.saveUserLocalAndRemoteUseCase.dispose();
        this.getUserRemotelyUseCase.dispose();
        this.getUserLocallyUseCase.dispose();
        this.getInitialProgressUseCase.dispose();
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
            this.getUserLocallyUseCase.execute(new LocalUserObserver(email), email);
        }
    }

    private void retryFirebaseSignUp(User user) {
        this.getInitialProgressUseCase.execute(new GetInitialProgressObserver(user),null);
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
        private String email;

        public LocalUserObserver(String email) {
            this.email = email;
        }

        @Override
        public void onNext(User localUser) {
            SignupPresenter.this.getUserRemotelyUseCase.execute(new RemoteUserObserver(remoteUser -> SignupPresenter.this.retryFirebaseSignUp(localUser)
            ), localUser.getMail());
        }

        @Override
        public void onError(Throwable exception) {
            if (exception instanceof LocalUserNotFoundException) {
                SignupPresenter.this.getUserRemotelyUseCase.execute(new RemoteUserObserver(remoteUser -> SignupPresenter.this.continueSignUp()), this.email);
            } else {
                SignupPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) exception));
                SignupPresenter.this.hideViewLoading();
                SignupPresenter.this.showViewRetry();
            }
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

    private final class RemoteUserObserver extends DefaultObserver<User> {
        private OnFinishValidation onFinish;

        public RemoteUserObserver(OnFinishValidation onFinish) {
            this.onFinish = onFinish;
        }

        @Override
        public void onNext(User remoteUser) {
            if (remoteUser.getUid() == null && this.onFinish != null) {
                this.onFinish.onValidationFinished(remoteUser);
            } else {
                SignupPresenter.this.hideViewLoading();
                ErrorBundle errorBundle = new DefaultErrorBundle(new UserAlreadyRegisteredException());
                SignupPresenter.this.showErrorMessage(errorBundle);
            }
        }

        @Override
        public void onError(Throwable exception) {
            SignupPresenter.this.hideViewLoading();
        }
    }

    private interface OnFinishValidation {
        void onValidationFinished(User remoteUser);
    }

    private final class GetInitialProgressObserver extends DefaultObserver<Progress> {
        private User user;

        public GetInitialProgressObserver(User user) {
            this.user = user;
        }

        @Override
        public void onNext(Progress progress) {
            ProgressModel initialProgress = SignupPresenter.this.userDataMapper.progressToProgressModel(progress);
            SignupPresenter.this.saveUserLocalAndRemoteUseCase.execute(new SaveUserLocalAndRemoteObserver(saveUserOverallDataUseCase, saveProgressUseCase, SignupPresenter.this, signupView, userDataMapper, initialProgress), user);
        }
    }

}