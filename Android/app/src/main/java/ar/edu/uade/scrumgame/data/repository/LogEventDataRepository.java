package ar.edu.uade.scrumgame.data.repository;

import ar.edu.uade.scrumgame.data.repository.datasource.LogEventDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.LogEventDataStoreFactory;
import ar.edu.uade.scrumgame.domain.repository.LogEventRepository;
import io.reactivex.Observable;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LogEventDataRepository implements LogEventRepository {
    private LogEventDataStoreFactory logEventDataStoreFactory;

    @Inject
    LogEventDataRepository(LogEventDataStoreFactory logEventDataStoreFactory) {
        this.logEventDataStoreFactory = logEventDataStoreFactory;
    }

    @Override
    public Observable<Void> logEvent(String eventName, JSONObject eventParams) {
        LogEventDataStore logEventDataStore = this.logEventDataStoreFactory.createAmplitudeDataStore();
        return logEventDataStore.logEvent(eventName,eventParams);
    }
}
