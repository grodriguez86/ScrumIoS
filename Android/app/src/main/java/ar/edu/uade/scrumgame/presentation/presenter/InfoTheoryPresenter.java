package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import java.util.Collection;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.SubLevel;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.DefaultObserver;
import ar.edu.uade.scrumgame.domain.interactor.GetSubLevel;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.LevelModelDataMapper;
import ar.edu.uade.scrumgame.presentation.models.InfoTheoryModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.view.InfoTheoryView;

@PerActivity
public class InfoTheoryPresenter implements Presenter {

    private InfoTheoryView infoTheoryView;
    private GetSubLevel getSubLevelUseCase;
    private LevelModelDataMapper levelModelDataMapper;

    @Inject
    InfoTheoryPresenter(GetSubLevel getSubLevelUseCase,
                        LevelModelDataMapper levelModelDataMapper) {
        this.getSubLevelUseCase = getSubLevelUseCase;
        this.levelModelDataMapper = levelModelDataMapper;
    }

    public void setView(@NonNull InfoTheoryView view) {
        this.infoTheoryView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getSubLevelUseCase.dispose();
        this.infoTheoryView = null;
    }

    public void initialize(String subLevelCode) {
        this.loadSubLevel(subLevelCode);
    }

    private void loadSubLevel(String subLevelCode) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getSubLevelUseCase.execute(new SubLevelObserver(), subLevelCode);
    }

    private void showViewLoading() {
        this.infoTheoryView.showLoading();
    }

    private void hideViewLoading() {
        this.infoTheoryView.hideLoading();
    }

    private void showViewRetry() {
        this.infoTheoryView.showRetry();
    }

    private void hideViewRetry() {
        this.infoTheoryView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.infoTheoryView.context(),
                errorBundle.getException());
        this.infoTheoryView.showError(errorMessage);
    }

    private void showInfoTheoryCollectionInView(Collection<InfoTheoryModel> infoTheoryModelCollection) {
        this.infoTheoryView.renderInfoTheoryList(infoTheoryModelCollection);
    }

    private void showSubLevelData(SubLevel subLevel) {
        SubLevelModel subLevelModel =
                this.levelModelDataMapper.convertSubLevelEntityToSubLevelModel(subLevel);
        showInfoTheoryCollectionInView(subLevelModel.getInfoTheory());
        this.infoTheoryView.loadSubLevel(subLevelModel);
    }

    private final class SubLevelObserver extends DefaultObserver<SubLevel> {

        @Override
        public void onComplete() {
            InfoTheoryPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            InfoTheoryPresenter.this.hideViewLoading();
            InfoTheoryPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            InfoTheoryPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(SubLevel subLevel) {
            InfoTheoryPresenter.this.showSubLevelData(subLevel);
        }
    }
}