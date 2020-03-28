package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.DefaultObserver;
import ar.edu.uade.scrumgame.domain.interactor.GetLevel;
import ar.edu.uade.scrumgame.domain.interactor.GetProgressListLocally;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.LevelModelDataMapper;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.view.LevelView;

@PerActivity
public class LevelPresenter implements Presenter {

    private LevelView levelView;
    private GetLevel getLevelUseCase;
    private LevelModelDataMapper levelModelDataMapper;
    private UserDataMapper userDataMapper;
    private GetProgressListLocally getProgressListLocallyUseCase;
    private Level level;

    @Inject
    LevelPresenter(GetLevel getLevelUseCase,
                   LevelModelDataMapper levelModelDataMapper,
                   GetProgressListLocally getProgressListLocallyUseCase,
                   UserDataMapper userDataMapper) {
        this.getLevelUseCase = getLevelUseCase;
        this.levelModelDataMapper = levelModelDataMapper;
        this.getProgressListLocallyUseCase = getProgressListLocallyUseCase;
        this.userDataMapper = userDataMapper;
    }

    public void setView(@NonNull LevelView view) {
        this.levelView = view;
    }

    @Override
    public void resume() {
        if (level != null)
            loadLevel(level.getCode());
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        level = null;
        this.getLevelUseCase.dispose();
        this.getProgressListLocallyUseCase.dispose();
        this.levelView = null;
    }

    public void initialize(Integer levelCode) {
        this.loadLevel(levelCode);
    }

    public void onSubLevelClicked(String levelName, SubLevelModel subLevelModel, ProgressModel progressModel) {
        this.levelView.enterSubLevel(levelName, subLevelModel, progressModel);
    }

    private void loadLevel(Integer levelCode) {
        level = null;
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

    private void showSubLevelCollectionInView(Collection<SubLevelModel> subLevelCollection,
                                              ProgressModel progressModel) {
        this.levelView.renderSubLevelList(subLevelCollection, progressModel);
    }

    private void showLevelData(Level level, Progress progress) {
        LevelModel levelModel =
                this.levelModelDataMapper.transform(level);
        ProgressModel progressModel = progress == null ?
                null :
                this.userDataMapper.progressToProgressModel(progress);
        showSubLevelCollectionInView(levelModel.getSublevels(), progressModel);
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
            LevelPresenter.this.level = level;
            getProgressListLocallyUseCase.execute(new ProgressObserver(), null);
        }
    }

    private final class ProgressObserver extends DefaultObserver<List<Progress>> {
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
        public void onNext(List<Progress> progressList) {
            int levelIndex = LevelPresenter.this.level.getCode() - 1;
            Progress progress = progressList.size() >= LevelPresenter.this.level.getCode() ?
                    progressList.get(levelIndex) :
                    null;
            LevelPresenter.this.showLevelData(level, progress);
        }

    }
}