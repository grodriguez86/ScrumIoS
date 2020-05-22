package ar.edu.uade.scrumgame.presentation.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import ar.edu.uade.scrumgame.domain.Params;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.interactor.*;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.mapper.UserDataMapper;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.models.UserModel;

import javax.inject.Inject;

@PerActivity
public class GamePresenter implements Presenter {
    private InitializeApplication initializeApplicationUseCase;
    private LogEvent logEventUseCase;
    private GetLoggedInUser getLoggedInUserUseCase;
    private UserDataMapper userDataMapper;
    private UserModel loggedInUser;

    @Inject
    GamePresenter(InitializeApplication initializeApplicationUseCase, LogEvent logEventUseCase, GetLoggedInUser getLoggedInUserUseCase, UserDataMapper userDataMapper) {
        this.initializeApplicationUseCase = initializeApplicationUseCase;
        this.logEventUseCase = logEventUseCase;
        this.getLoggedInUserUseCase = getLoggedInUserUseCase;
        this.userDataMapper = userDataMapper;
    }

    public void initialize() {
        this.initializeApplicationUseCase.execute(new DefaultObserver<>(), null);
        this.getLoggedInUserUseCase.execute(new GetLoggedInUserObserver(), false);
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.initializeApplicationUseCase.dispose();
        this.logEventUseCase.dispose();
        this.getLoggedInUserUseCase.dispose();
    }

    public void logEventGameCorrectAnswer(InfoGameModel gameModel, String timeSpent) {
        this.doLog(gameModel, timeSpent, LogEvent.GAME_CORRECT_ANSWER);
    }

    public void logEventGameCorrectAnswerFirstTry(InfoGameModel gameModel, String timeSpent) {
        this.doLog(gameModel, timeSpent, LogEvent.GAME_CORRECT_ANSWER_FIRST_TRY);
    }

    public void logEventGameWrongAnswer(InfoGameModel gameModel, String timeSpent) {
        this.doLog(gameModel, timeSpent, LogEvent.GAME_WRONG_ANSWER);
    }

    private void doLog(InfoGameModel gameModel, String timeSpent, String event) {
        if (this.loggedInUser != null) {
            String parsedLevelCode = this.parseLevelCode(gameModel.getCode());
            Params params = Params.build();
            params.putString(LogEvent.KEY_EVENT, event);
            params.putString(LogEvent.KEY_LEVEL, parsedLevelCode);
            params.putString(LogEvent.KEY_VALUE, timeSpent);
            params.putString(LogEvent.KEY_ID, this.loggedInUser.getUid());
            this.logEventUseCase.execute(new LogEventObserver(), params);
        }
    }

    @SuppressLint("DefaultLocale")
    private String parseLevelCode(String code) {
        String[] splitGameCode = code.split("\\.");
        int levelCode = Integer.parseInt(splitGameCode[0]);
        int subLevelCode = Integer.parseInt(splitGameCode[1]);
        int currentGameIndex = Integer.parseInt(splitGameCode[2]) - 1;
        return String.format("%d.%d.%d", levelCode, subLevelCode, currentGameIndex);
    }


    private static final class LogEventObserver extends DefaultObserver<Void> {
        @Override
        public void onError(Throwable e) {
            Log.e("LOG ERROR", e.getMessage(), e);
        }
    }

    private final class GetLoggedInUserObserver extends DefaultObserver<User> {
        @Override
        public void onNext(User loggedInUser) {
            GamePresenter.this.loggedInUser = GamePresenter.this.userDataMapper.userToUserModel(loggedInUser);
        }
    }
}