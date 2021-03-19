package ar.edu.uade.scrumgame.presentation.presenter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.data.entity.LevelStatusConstants;
import ar.edu.uade.scrumgame.domain.InfoGame;
import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.*;
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
    private GetProgressLocally getProgressLocallyUseCase;
    private SaveProgress saveProgressUseCase;
    private GetLevel getLevelUseCase;
    private UserDataMapper userDataMapper;
    private InfoGameModelDataMapper infoGameModelDataMapper;
    private List<InfoGameModel> infoGameModels;
    private String subLevelCode;
    private Progress levelProgress;
    private Integer currentGame;
    private Integer levelCode;
    private Integer subLevelId;

    @Inject
    InfoGamesContentPresenter(GetInfoGame getInfoGameUseCase,
                              InfoGameModelDataMapper infoGameModelDataMapper,
                              UserDataMapper userDataMapper,
                              GetProgressLocally getProgressLocallyUseCase,
                              SaveProgress saveProgressUseCase,
                              GetLevel getLevelUseCase) {
        this.getInfoGameUseCase = getInfoGameUseCase;
        this.infoGameModelDataMapper = infoGameModelDataMapper;
        this.userDataMapper = userDataMapper;
        this.getProgressLocallyUseCase = getProgressLocallyUseCase;
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
        this.getProgressLocallyUseCase.dispose();
        this.infoGamesContentView = null;
    }

    public void initialize(String subLevelCode) {
        this.subLevelCode = subLevelCode;
        this.levelCode = Integer.parseInt(subLevelCode.split("\\.")[0]);
        this.subLevelId = Integer.parseInt(subLevelCode.split("\\.")[1]);
        this.loadSubLevel();
    }

    private void loadSubLevel() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getProgressLocallyUseCase.execute(new ProgressObserver(), this.levelCode);
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
        this.infoGameModels = new ArrayList<>(infoGameModelCollection);
        this.playGame();
    }

    private void playGame() {
        if (this.areGamesPendingToPlay()) {
            InfoGameModel currentGame = this.infoGameModels.get(this.currentGame);
            Float progress = (float) this.currentGame / this.infoGameModels.size() * 100;
            this.infoGamesContentView.playGame(currentGame, progress);
        } else {
            this.infoGamesContentView.showLevelCompletedView();
        }
    }

    private Boolean areGamesPendingToPlay() {
        return this.currentGame < this.infoGameModels.size();
    }

    public void playNextLevel(String gameCode) {
        if (!this.isGameCompleted()) {
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
            this.levelProgress = userDataMapper.progressModelToProgress(updatedProgress);
            saveProgressUseCase.execute(new SaveProgressAndPlayNextObserver(),
                    this.levelProgress);
        } else {
            this.currentGame++;
            this.playGame();
        }
    }

    public Boolean isGameCompleted() {
        return !(this.isCurrentProgressSameAsSaved() || this.isSubLevelNotStarted());
    }

    public Boolean isCurrentProgressSameAsSaved() {
        return this.isSameGame() && this.isSameSubLevel() && this.isSameLevel();
    }

    public Boolean isSameLevel() {
        return this.levelCode.equals(this.levelProgress.getLevelId());
    }

    public Boolean isSameSubLevel() {
        return this.subLevelId.equals(this.levelProgress.getSublevelID());
    }

    public Boolean isSameGame() {
        return this.currentGame.equals(this.levelProgress.getActualGame());
    }

    public Boolean isSubLevelNotStarted() {
        return this.isSameLevel() && this.levelProgress.getSublevelID() == 0;
    }

    public void playPreviousLevel() {
        if (!this.isFirstGame()) {
            this.currentGame--;
            this.playGame();
        }
    }

    public Boolean isFirstGame() {
        return this.currentGame == 0;
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
        if (!this.isGameCompleted()) {
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
            this.levelProgress = userDataMapper.progressModelToProgress(updatedProgress);
            saveProgressUseCase.execute(new SaveProgressAndFinishObserver(),
                    this.levelProgress);
        } else {
            infoGamesContentView.goToSublevelMenu();
        }
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
            InfoGamesContentPresenter.this.currentGame++;
            InfoGamesContentPresenter.this.playGame();
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
            Integer subLevelCount = level.getSublevels().size();
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
                InfoGamesContentPresenter.this.levelProgress = userDataMapper.progressModelToProgress(updatedProgress);
                saveProgressUseCase.execute(new UpdateNextLevelProgressObserver(),
                        InfoGamesContentPresenter.this.levelProgress);
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

    private final class ProgressObserver extends DefaultObserver<Progress> {

        @Override
        public void onNext(Progress progress) {
            InfoGamesContentPresenter.this.levelProgress = progress;
            InfoGamesContentPresenter.this.currentGame = InfoGamesContentPresenter.this.subLevelId.equals(progress.getSublevelID()) && progress.getActualGame() > 0 ? progress.getActualGame() : 0;
            InfoGamesContentPresenter.this.getInfoGameUseCase.execute(new InfoGameObserver(), subLevelCode);
        }
    }


}
