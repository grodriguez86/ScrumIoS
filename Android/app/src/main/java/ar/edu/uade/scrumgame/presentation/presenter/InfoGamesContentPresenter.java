package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.data.entity.LevelStatusConstants;
import ar.edu.uade.scrumgame.domain.InfoGame;
import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.DefaultObserver;
import ar.edu.uade.scrumgame.domain.interactor.GetInfoGame;
import ar.edu.uade.scrumgame.domain.interactor.GetLevel;
import ar.edu.uade.scrumgame.domain.interactor.SaveProgress;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.InfoGameModelDataMapper;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.view.InfoGamesContentView;

@PerActivity
public class InfoGamesContentPresenter implements Presenter {

    private InfoGamesContentView infoGamesContentView;
    private GetInfoGame getInfoGameUseCase;
    private SaveProgress saveProgressUseCase;
    private GetLevel getLevelUseCase;
    private UserDataMapper userDataMapper;
    private InfoGameModelDataMapper infoGameModelDataMapper;
    private List<InfoGameModel> infoGameModels;
    private Integer subLevelCount;

    @Inject
    InfoGamesContentPresenter(GetInfoGame getInfoGameUseCase,
                              InfoGameModelDataMapper infoGameModelDataMapper,
                              UserDataMapper userDataMapper,
                              SaveProgress saveProgressUseCase,
                              GetLevel getLevelUseCase) {
        this.getInfoGameUseCase = getInfoGameUseCase;
        this.infoGameModelDataMapper = infoGameModelDataMapper;
        this.userDataMapper = userDataMapper;
        this.saveProgressUseCase = saveProgressUseCase;
        this.getLevelUseCase = getLevelUseCase;
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
        this.infoGameModels = infoGameModelList;
        this.infoGamesContentView.loadGames(infoGameModelList);
    }

    public void playNextLevel(String gameCode) {
        this.showViewLoading();
        String[] splitGameCode = gameCode.split("\\.");
        int levelCode = Integer.parseInt(splitGameCode[0]);
        int sublevelCode = Integer.parseInt(splitGameCode[1]);
        int currentGameIndex = Integer.parseInt(splitGameCode[2]) - 1;
        ProgressModel updatedProgress = new ProgressModel(
                levelCode,
                sublevelCode,
                true,
                currentGameIndex == infoGameModels.size() ?
                        currentGameIndex :
                        currentGameIndex + 1,
                infoGameModels.size(),
                LevelStatusConstants.STARTED,
                false
        );
        saveProgressUseCase.execute(new SaveProgressAndPlayNextObserver(),
                userDataMapper.progressModelToProgress(updatedProgress));
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

    public void finishSublevel() {
        this.showViewLoading();
        InfoGameModel firstInfoGameModel = infoGameModels.get(0);
        ProgressModel updatedProgress = new ProgressModel(
                firstInfoGameModel.getLevelCode(),
                firstInfoGameModel.getSubLevelCode() + 1,
                true,
                0,
                infoGameModels.size(),
                LevelStatusConstants.STARTED,
                false
        );
        saveProgressUseCase.execute(new SaveProgressAndFinishObserver(),
                userDataMapper.progressModelToProgress(updatedProgress));
    }

    private final class SaveProgressAndPlayNextObserver extends DefaultObserver<String> {
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
        public void onNext(String result) {
            InfoGamesContentPresenter.this.hideViewLoading();
            InfoGamesContentPresenter.this.infoGamesContentView.playNextLevel();
        }
    }

    private final class SaveProgressAndFinishObserver extends DefaultObserver<String> {
        @Override
        public void onError(Throwable e) {
            InfoGamesContentPresenter.this.hideViewLoading();
            InfoGamesContentPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            InfoGamesContentPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(String result) {
            InfoGameModel firstInfoGameModel = infoGameModels.get(0);
            getLevelUseCase.execute(new GetLevelLocallyObserver(), firstInfoGameModel.getLevelCode());
        }
    }

    private final class GetLevelLocallyObserver extends DefaultObserver<Level> {

        @Override
        public void onError(Throwable e) {
            InfoGamesContentPresenter.this.hideViewLoading();
            InfoGamesContentPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            InfoGamesContentPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Level level) {
            subLevelCount = level.getSublevels().size();
            InfoGameModel firstInfoGameModel = infoGameModels.get(0);
            if (subLevelCount == firstInfoGameModel.getSubLevelCode()) {
                ProgressModel updatedProgress = new ProgressModel(
                        firstInfoGameModel.getLevelCode() + 1,
                        0,
                        false,
                        0,
                        0,
                        LevelStatusConstants.NOT_STARTED,
                        false
                );
                saveProgressUseCase.execute(new UpdateNextLevelProgressObserver(),
                        userDataMapper.progressModelToProgress(updatedProgress));
            } else {
                InfoGamesContentPresenter.this.hideViewLoading();
                infoGamesContentView.goToSublevelMenu();
            }
        }
    }

    private final class UpdateNextLevelProgressObserver extends DefaultObserver<String> {
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
        public void onNext(String result) {
            InfoGamesContentPresenter.this.hideViewLoading();
            infoGamesContentView.goToSublevelMenu();
        }
    }

}