package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import java.util.Collection;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.DefaultObserver;
import ar.edu.uade.scrumgame.domain.interactor.GetLevel;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.LevelModelDataMapper;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.view.LevelView;

@PerActivity
public class LevelPresenter implements Presenter {

    private LevelView levelView;
    private GetLevel getLevelUseCase;
    private LevelModelDataMapper levelModelDataMapper;

    @Inject
    LevelPresenter(GetLevel getLevelUseCase,
                  LevelModelDataMapper levelModelDataMapper) {
        this.getLevelUseCase = getLevelUseCase;
        this.levelModelDataMapper = levelModelDataMapper;
    }

    public void setView(@NonNull LevelView view) {
        this.levelView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getLevelUseCase.dispose();
        this.levelView = null;
    }

    public void initialize(Integer levelCode) {
        this.loadLevel(levelCode);
    }

    public void onSubLevelClicked(String levelName, SubLevelModel subLevelModel) {
        this.levelView.enterSubLevel(levelName, subLevelModel);
    }

    private void loadLevel(Integer levelCode) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getLevelUseCase.execute(new LevelObserver(), levelCode);
    }

    private void showViewLoading() {
        this.levelView.showLoading();
    }

    private void hideViewLoading() {
        this.levelView.hideLoading();
    }

    private void showViewRetry() {
        this.levelView.showRetry();
    }

    private void hideViewRetry() {
        this.levelView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.levelView.context(),
                errorBundle.getException());
        this.levelView.showError(errorMessage);
    }

    private void showSubLevelCollectionInView(Collection<SubLevelModel> subLevelCollection) {
        this.levelView.renderSubLevelList(subLevelCollection);
    }

    private void showLevelData(Level level) {
        LevelModel levelModel =
                this.levelModelDataMapper.transform(level);
        showSubLevelCollectionInView(levelModel.getSublevels());
        this.levelView.loadLevel(levelModel);
    }

    private final class LevelObserver extends DefaultObserver<Level> {

        @Override
        public void onComplete() {
            LevelPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            LevelPresenter.this.hideViewLoading();
            LevelPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            LevelPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Level level) {
            LevelPresenter.this.showLevelData(level);
        }
    }
}