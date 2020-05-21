package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.SaveProgress;
import ar.edu.uade.scrumgame.domain.interactor.SaveUserLocalAndRemote;
import ar.edu.uade.scrumgame.domain.interactor.SaveUserOverallData;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.UserModel;
import ar.edu.uade.scrumgame.presentation.presenter.observers.SaveUserLocalAndRemoteObserver;
import ar.edu.uade.scrumgame.presentation.view.SignupDetailsView;

@PerActivity
public class SignupDetailsPresenter implements SignUpBasePresenter {

    private SaveProgress saveProgressUseCase;
    private SaveUserLocalAndRemote saveUserLocalAndRemoteUseCase;
    private SaveUserOverallData saveUserOverallDataUseCase;
    private SignupDetailsView signupDetailsView;
    private UserDataMapper userDataMapper;

    @Inject
    SignupDetailsPresenter(SaveProgress saveProgressUseCase,
                           SaveUserLocalAndRemote saveUserLocalAndRemoteUseCase,
                           SaveUserOverallData saveUserOverallDataUseCase,
                           UserDataMapper userDataMapper
    ) {
        this.saveProgressUseCase = saveProgressUseCase;
        this.saveUserLocalAndRemoteUseCase = saveUserLocalAndRemoteUseCase;
        this.saveUserOverallDataUseCase = saveUserOverallDataUseCase;
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
        this.saveUserOverallDataUseCase.dispose();
        this.saveUserLocalAndRemoteUseCase.dispose();
    }

    public void initialize() {

    }

    public void onDoneClicked(UserModel userDetails, ProgressModel initialProgress) {
        this.showViewLoading();
        saveUserLocalAndRemoteUseCase.execute(new SaveUserLocalAndRemoteObserver(saveUserOverallDataUseCase, saveProgressUseCase, this, signupDetailsView, userDataMapper, initialProgress), userDataMapper.userModelToUser(userDetails));
    }

    private void showViewLoading() {
        this.signupDetailsView.showLoading();
    }

    @Override
    public void hideViewLoading() {
        this.signupDetailsView.hideLoading();
    }

    @Override
    public void showViewRetry() {
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