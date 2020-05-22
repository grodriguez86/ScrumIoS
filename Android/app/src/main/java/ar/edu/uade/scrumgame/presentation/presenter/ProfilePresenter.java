package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.*;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.view.ProfileView;

import javax.inject.Inject;

@PerActivity
public class ProfilePresenter implements Presenter {

    private ProfileView profileView;
    private GetLoggedInUser getLoggedInUserUseCase;
    private LogOut logOutUseCase;
    private UserDataMapper userDataMapper;

    @Inject
    ProfilePresenter(GetLoggedInUser getLoggedInUserUseCase, LogOut logOutUseCase,
                     UserDataMapper userDataMapper) {
        this.getLoggedInUserUseCase = getLoggedInUserUseCase;
        this.logOutUseCase = logOutUseCase;
        this.userDataMapper = userDataMapper;
    }

    public void setView(@NonNull ProfileView view) {
        this.profileView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getLoggedInUserUseCase.dispose();
        this.logOutUseCase.dispose();
        this.profileView = null;
    }

    public void initialize() {
        this.showViewLoading();
        this.getLoggedInUser();
    }

    private void getLoggedInUser() {
        this.getLoggedInUserUseCase.execute(new GetLoggedInUserObserver(), false);
    }

    private void showViewLoading() {
        this.profileView.showLoading();
    }

    private void hideViewLoading() {
        this.profileView.hideLoading();
    }

    private void showViewRetry() {
        this.profileView.showRetry();
    }

    private void hideViewRetry() {
        this.profileView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.profileView.context(),
                errorBundle.getException());
        this.profileView.showError(errorMessage);
    }

    public void onLogout() {
        this.logOutUseCase.execute(new DefaultObserver<Void>(){
            @Override
            public void onComplete() {
                ProfilePresenter.this.profileView.onLoggedOut();
            }
        },null);
    }

    private final class GetLoggedInUserObserver extends DefaultObserver<User> {
        @Override
        public void onNext(User loggedInUser) {
            ProfilePresenter.this.hideViewLoading();
            ProfilePresenter.this.profileView.profileLoaded(ProfilePresenter.this.userDataMapper.userToUserModel(loggedInUser));
        }

        @Override
        public void onError(Throwable exception) {
            ProfilePresenter.this.hideViewLoading();
            ProfilePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) exception));
            ProfilePresenter.this.showViewRetry();
        }
    }

}