package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.DefaultObserver;
import ar.edu.uade.scrumgame.domain.interactor.GetLevelList;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.LevelModelDataMapper;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.view.LevelListView;
import io.realm.Realm;

@PerActivity
public class MenuPresenter implements Presenter {

    private LevelListView levelListView;
    private GetLevelList getLevelListUseCase;
    private LevelModelDataMapper levelModelDataMapper;

    @Inject
    MenuPresenter(GetLevelList getLevelListUseCase,
                         LevelModelDataMapper levelModelDataMapper) {
        this.getLevelListUseCase = getLevelListUseCase;
        this.levelModelDataMapper = levelModelDataMapper;
    }

    public void setView(@NonNull LevelListView view) {
        this.levelListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getLevelListUseCase.dispose();
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

    private void showLevelCollectionInView(Collection<Level> levelCollection) {
        Collection<LevelModel> levelModelCollection =
                this.levelModelDataMapper.transform(levelCollection);
        this.levelListView.renderLevelList(levelModelCollection);
    }

    private final class LevelListObserver extends DefaultObserver<List<Level>> {

        @Override
        public void onComplete() {
            MenuPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            MenuPresenter.this.hideViewLoading();
            MenuPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            MenuPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Level> levelList) {
            MenuPresenter.this.showLevelCollectionInView(levelList);
        }
    }
}