package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.DefaultObserver;
import ar.edu.uade.scrumgame.domain.interactor.SaveProgress;
import ar.edu.uade.scrumgame.domain.interactor.SaveUser;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.UserModel;
import ar.edu.uade.scrumgame.presentation.view.SignupDetailsView;
import ar.edu.uade.scrumgame.domain.interactor.SaveProgress.PROGRESS_SAVE_OUTCOMES;
import ar.edu.uade.scrumgame.domain.interactor.SaveUser.USER_SAVE_OUTCOMES;

@PerActivity
public class SignupDetailsPresenter implements Presenter {

    private SaveProgress saveProgressUseCase;
    private SaveUser saveUserUseCase;
    private SignupDetailsView signupDetailsView;
    private UserDataMapper userDataMapper;

    @Inject
    SignupDetailsPresenter(SaveProgress saveProgressUseCase, SaveUser saveUserUseCase, UserDataMapper userDataMapper) {
        this.saveProgressUseCase = saveProgressUseCase;
        this.saveUserUseCase = saveUserUseCase;
        this.userDataMapper = userDataMapper;
    }

    public void setView(@NonNull SignupDetailsView view) {
        this.signupDetailsView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.signupDetailsView = null;
        this.saveProgressUseCase.dispose();
    }

    public void initialize() {

    }

    public void onDoneClicked(UserModel userDetails, ProgressModel initialProgress) {
        SignupDetailsPresenter.this.showViewLoading();
        saveUserUseCase.execute(new DefaultObserver<String>() {
            @Override
            public void onNext(String saveUserOutcome) {
                switch (saveUserOutcome) {
                    case USER_SAVE_OUTCOMES.COMPLETE:
                    case USER_SAVE_OUTCOMES.FAILED_REMOTE:
                        saveProgressUseCase.execute(new DefaultObserver<String>() {
                            @Override
                            public void onNext(String saveProgressOutcome) {
                                switch (saveProgressOutcome) {
                                    case PROGRESS_SAVE_OUTCOMES.COMPLETE:
                                        SignupDetailsPresenter.this.hideViewLoading();
                                        signupDetailsView.enterMenu();
                                        break;
                                    case PROGRESS_SAVE_OUTCOMES.FAILED_REMOTE:
                                        SignupDetailsPresenter.this.hideViewLoading();
                                        signupDetailsView.showError(signupDetailsView.context().getString(R.string.error_saving_remote_data));
                                        signupDetailsView.returnToLogin();
                                        break;
                                    default:
                                        SignupDetailsPresenter.this.hideViewLoading();
                                        signupDetailsView.showError(signupDetailsView.context().getString(R.string.unknown_error));
                                        SignupDetailsPresenter.this.showViewRetry();
                                }
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable exception) {
                                SignupDetailsPresenter.this.hideViewLoading();
                                signupDetailsView.showError(signupDetailsView.context().getString(R.string.error_saving_progress));
                                SignupDetailsPresenter.this.showViewRetry();
                            }
                        }, userDataMapper.progressModelToProgress(initialProgress));
                        break;
                    default:
                        SignupDetailsPresenter.this.hideViewLoading();
                        signupDetailsView.showError(signupDetailsView.context().getString(R.string.unknown_error));
                        SignupDetailsPresenter.this.showViewRetry();
                        break;
                }
            }
            @Override
            public void onError(Throwable exception) {
                SignupDetailsPresenter.this.hideViewLoading();
                signupDetailsView.showError(signupDetailsView.context().getString(R.string.error_saving_user));
                SignupDetailsPresenter.this.showViewRetry();
            }
        }, userDataMapper.userModelToUser(userDetails));

    }

    private void showViewLoading() {
        this.signupDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.signupDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.signupDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.signupDetailsView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.signupDetailsView.context(),
                errorBundle.getException());
        this.signupDetailsView.showError(errorMessage);
    }
}