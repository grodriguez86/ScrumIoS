package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.InfoGame;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.DefaultObserver;
import ar.edu.uade.scrumgame.domain.interactor.GetInfoGame;
import ar.edu.uade.scrumgame.domain.interactor.SaveProgress;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.InfoGameModelDataMapper;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.view.InfoGamesContentView;

@PerActivity
public class InfoGamesContentPresenter implements Presenter {

    private InfoGamesContentView infoGamesContentView;
    private GetInfoGame getInfoGameUseCase;
    //TODO progreso
    //private SaveProgress saveProgressUseCase;
    private InfoGameModelDataMapper infoGameModelDataMapper;

    @Inject
    InfoGamesContentPresenter(GetInfoGame getInfoGameUseCase,
                              InfoGameModelDataMapper infoGameModelDataMapper) {
        this.getInfoGameUseCase = getInfoGameUseCase;
        this.infoGameModelDataMapper = infoGameModelDataMapper;
    }

    public void setView(@NonNull InfoGamesContentView view) {
        this.infoGamesContentView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getInfoGameUseCase.dispose();
        this.infoGamesContentView = null;
    }

    public void initialize(String subLevelCode) {
        this.loadSubLevel(subLevelCode);
    }

    private void loadSubLevel(String subLevelCode) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getInfoGameUseCase.execute(new InfoGameObserver(), subLevelCode);
    }

    private void showViewLoading() {
        this.infoGamesContentView.showLoading();
    }

    private void hideViewLoading() {
        this.infoGamesContentView.hideLoading();
    }

    private void showViewRetry() {
        this.infoGamesContentView.showRetry();
    }

    private void hideViewRetry() {
        this.infoGamesContentView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.infoGamesContentView.context(),
                errorBundle.getException());
        this.infoGamesContentView.showError(errorMessage);
    }

    private void setGames(Collection<InfoGame> infoGameCollection) {
        Collection<InfoGameModel> infoGameModelCollection =
                this.infoGameModelDataMapper.transform(infoGameCollection);
        List<InfoGameModel> infoGameModelList = new ArrayList<>(infoGameModelCollection);
        this.infoGamesContentView.loadGames(infoGameModelList);
    }

    public void saveCompleteGameProgress(String gameCode) {
        //TODO progreso
        this.infoGamesContentView.playNextLevel();
    }

    private final class InfoGameObserver extends DefaultObserver<Collection<InfoGame>> {

        @Override
        public void onComplete() {
            InfoGamesContentPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            InfoGamesContentPresenter.this.hideViewLoading();
            InfoGamesContentPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            InfoGamesContentPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Collection<InfoGame> infoGameCollection) {
            InfoGamesContentPresenter.this.setGames(infoGameCollection);
        }
    }
}