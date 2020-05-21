package ar.edu.uade.scrumgame.presentation.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import androidx.annotation.NonNull;

import java.util.Collection;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Params;
import ar.edu.uade.scrumgame.domain.SubLevel;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.exception.DefaultErrorBundle;
import ar.edu.uade.scrumgame.domain.exception.ErrorBundle;
import ar.edu.uade.scrumgame.domain.interactor.*;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.exception.ErrorMessageFactory;
import ar.edu.uade.scrumgame.presentation.mapper.LevelModelDataMapper;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.models.InfoTheoryModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.models.UserModel;
import ar.edu.uade.scrumgame.presentation.view.InfoTheoryView;

@PerActivity
public class InfoTheoryPresenter implements Presenter {

    private InfoTheoryView infoTheoryView;
    private GetSubLevel getSubLevelUseCase;
    private LogEvent logEventUseCase;
    private GetLoggedInUser getLoggedInUser;
    private UserDataMapper userDataMapper;
    private LevelModelDataMapper levelModelDataMapper;
    private UserModel loggedInUser;

    @Inject
    InfoTheoryPresenter(GetSubLevel getSubLevelUseCase, LogEvent logEventUseCase,
                        GetLoggedInUser getLoggedInUser, UserDataMapper userDataMapper,
                        LevelModelDataMapper levelModelDataMapper) {
        this.getSubLevelUseCase = getSubLevelUseCase;
        this.logEventUseCase = logEventUseCase;
        this.getLoggedInUser=getLoggedInUser;
        this.levelModelDataMapper = levelModelDataMapper;
        this.userDataMapper = userDataMapper;
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
        this.getLoggedInUser.dispose();
        this.infoTheoryView = null;
    }

    public void initialize(String subLevelCode) {
        this.getLoggedInUser.execute(new GetLoggedInUserObserver(),null);
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

    public void logTutorialSkipped( String subLevelCode) {
        this.doLog(subLevelCode, null, LogEvent.TUTORIAL_SKIPPED);
    }

    public void logTutorialTimeSpent(String subLevelCode, Integer secondsSpent) {
        this.doLog(subLevelCode, secondsSpent, LogEvent.TUTORIAL_TIME_SPENT);
    }

    @SuppressLint("DefaultLocale")
    private void doLog(String subLevelCode, Integer secondsSpent, String event) {
        if (this.loggedInUser != null) {
            String parsedLevelCode = parseLevelCode(subLevelCode);
            Params params = Params.build();
            params.putString(LogEvent.KEY_EVENT, event);
            params.putString(LogEvent.KEY_LEVEL, parsedLevelCode);
            if (secondsSpent != null) {
                params.putString(LogEvent.KEY_VALUE, String.valueOf(secondsSpent));
            }
            params.putString(LogEvent.KEY_ID, this.loggedInUser.getUid());
            this.logEventUseCase.execute(new LogEventObserver(), params);
        }
    }

    @SuppressLint("DefaultLocale")
    private String parseLevelCode(String code) {
        String[] splitGameCode = code.split("\\.");
        int levelCode = Integer.parseInt(splitGameCode[0]);
        int subLevelCode = Integer.parseInt(splitGameCode[1]);
        return String.format("%d.%d", levelCode, subLevelCode);
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

    private final class GetLoggedInUserObserver extends DefaultObserver<User> {
        @Override
        public void onNext(User loggedInUser) {
            InfoTheoryPresenter.this.loggedInUser = InfoTheoryPresenter.this.userDataMapper.userToUserModel(loggedInUser);
        }
    }


    private static final class LogEventObserver extends DefaultObserver<Void> {
        @Override
        public void onError(Throwable e) {
            Log.e("LOG ERROR", e.getMessage(), e);
        }
    }
}