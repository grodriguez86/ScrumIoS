package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.UserOverallData;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.DefaultObserver;
import ar.edu.uade.scrumgame.domain.interactor.GetLevelList;
import ar.edu.uade.scrumgame.domain.interactor.GetProgressListLocally;
import ar.edu.uade.scrumgame.domain.interactor.GetUserOverallData;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.LevelModelDataMapper;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.UserOverallDataModel;
import ar.edu.uade.scrumgame.presentation.view.LevelListView;
import io.realm.Realm;

@PerActivity
public class MenuPresenter implements Presenter {

    private LevelListView levelListView;
    private GetLevelList getLevelListUseCase;
    private GetProgressListLocally getProgressListLocallyUseCase;
    private GetUserOverallData getUserOverallDataUseCase;
    private LevelModelDataMapper levelModelDataMapper;
    private UserDataMapper userDataMapper;
    private List<Level> levelList;
    private List<Progress> levelProgressList;
    private UserOverallData userOverallData;


    @Inject
    MenuPresenter(GetLevelList getLevelListUseCase,
                  LevelModelDataMapper levelModelDataMapper,
                  GetProgressListLocally getProgressListLocallyUseCase,
                  GetUserOverallData getUserOverallDataUseCase,
                  UserDataMapper userDataMapper) {
        this.getLevelListUseCase = getLevelListUseCase;
        this.levelModelDataMapper = levelModelDataMapper;
        this.getProgressListLocallyUseCase = getProgressListLocallyUseCase;
        this.getUserOverallDataUseCase = getUserOverallDataUseCase;
        this.userDataMapper = userDataMapper;
    }

    public void setView(@NonNull LevelListView view) {
        this.levelListView = view;
    }

    @Override
    public void resume() {
        loadLevels();
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getLevelListUseCase.dispose();
        this.getProgressListLocallyUseCase.dispose();
        this.getUserOverallDataUseCase.dispose();
        this.levelListView = null;
    }

    public void initialize() {
        this.loadLevels();
    }

    public void onLevelClicked(LevelModel levelModel) {
        this.levelListView.enterLevel(levelModel);
    }

    private void loadLevels() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getLevelListUseCase.execute(new LevelListObserver(), null);
    }

    private void showViewLoading() {
        this.levelListView.showLoading();
    }

    private void hideViewLoading() {
        this.levelListView.hideLoading();
    }

    private void showViewRetry() {
        this.levelListView.showRetry();
    }

    private void hideViewRetry() {
        this.levelListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.levelListView.context(),
                errorBundle.getException());
        this.levelListView.showError(errorMessage);
    }

    private void showLevelCollectionInView(Collection<Level> levelCollection, Collection<Progress> progressCollection, UserOverallData userOverallData) {
        Collection<LevelModel> levelModelCollection =
                this.levelModelDataMapper.transform(levelCollection);
        Collection<ProgressModel> progressModelCollection =
                this.userDataMapper.progressToProgressModel(progressCollection);
        UserOverallDataModel userOverallDataModel =
                this.userDataMapper.userOverallDataToUserOverallDataModel(userOverallData);
        this.levelListView.renderLevelList(levelModelCollection, progressModelCollection, userOverallDataModel);
    }

    // OBSERVERS //
    private void onObserverError(Throwable exception){
        MenuPresenter.this.hideViewLoading();
        MenuPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) exception));
        MenuPresenter.this.showViewRetry();
    };

    private final class LevelListObserver extends DefaultObserver<List<Level>> {

        @Override
        public void onError(Throwable e) {
            MenuPresenter.this.onObserverError(e);
        }

        @Override
        public void onNext(List<Level> levelList) {
            MenuPresenter.this.levelList = levelList;
            getProgressListLocallyUseCase.execute(new ProgressListObserver(), null);
        }
    }

    private final class ProgressListObserver extends DefaultObserver<List<Progress>> {
        @Override
        public void onNext(List<Progress> progresses) {
            MenuPresenter.this.levelProgressList = progresses;
            getUserOverallDataUseCase.execute(new UserOverallDataObserver(), null);
        }

        @Override
        public void onError(Throwable exception) {
            MenuPresenter.this.onObserverError(exception);
        }
    }

    private final class UserOverallDataObserver extends DefaultObserver<UserOverallData> {
        @Override
        public void onNext(UserOverallData userOverallData) {
            MenuPresenter.this.userOverallData = userOverallData;
            MenuPresenter.this.hideViewLoading();
            MenuPresenter.this.showLevelCollectionInView(
                    MenuPresenter.this.levelList, MenuPresenter.this.levelProgressList, MenuPresenter.this.userOverallData);
        }

        @Override
        public void onError(Throwable exception) {
            MenuPresenter.this.onObserverError(exception);
        }
    }

}