package ar.edu.uade.scrumgame.domain.interactor;

import ar.edu.uade.scrumgame.domain.Params;
import ar.edu.uade.scrumgame.domain.exception.EmptyParamsException;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LogEventRepository;
import io.reactivex.Observable;
import org.json.JSONObject;

import javax.inject.Inject;

public class LogEvent extends UseCase<Void, Params> {
    public static String KEY_ID = "uuid";
    public static String KEY_EVENT = "type";
    public static String KEY_LEVEL = "level";
    public static String KEY_VALUE = "value";
    public static String GAME_CORRECT_ANSWER_FIRST_TRY = "game_correct_answer_in_first_try";
    public static String GAME_CORRECT_ANSWER = "game_correct_answer";
    public static String GAME_WRONG_ANSWER = "game_wrong_answer";
    public static String TUTORIAL_TIME_SPENT = "tutorial_time_spent";
    public static String TUTORIAL_SKIPPED = "tutorial_skipped";
    private static String DEFAULT_KEY_EVENT = "missing_event";
    private final LogEventRepository logEventRepository;

    @Inject
    LogEvent(LogEventRepository logEventRepository, ThreadExecutor threadExecutor,
             PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.logEventRepository = logEventRepository;
    }

    @Override
    Observable<Void> buildUseCaseObservable(Params params) {
        if (params != null) {
            JSONObject eventParams = new JSONObject(params.toStringMap());
            String eventName = params.getString(KEY_EVENT, DEFAULT_KEY_EVENT);
            return this.logEventRepository.logEvent(eventName, eventParams);
        }
        return Observable.create(emitter -> emitter.onError(new EmptyParamsException()));
    }
}
