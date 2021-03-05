package ar.edu.uade.scrumgame.data.repository;

import ar.edu.uade.scrumgame.data.repository.datasource.LogEventDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.LogEventDataStoreFactory;
import ar.edu.uade.scrumgame.domain.repository.LogEventRepository;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
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
        LogEventDataStore amplitudeDataStore = this.logEventDataStoreFactory.createAmplitudeDataStore();
        LogEventDataStore analyticsDataStore = this.logEventDataStoreFactory.createAnalyticsDataStore();
        return Observable.merge(
                amplitudeDataStore.logEvent(eventName, eventParams).subscribeOn(Schedulers.io()),
                analyticsDataStore.logEvent(eventName, eventParams).subscribeOn(Schedulers.io()));
    }

    @Override
    public Observable<Void> initialize() {
        LogEventDataStore amplitudeDataStore = this.logEventDataStoreFactory.createAmplitudeDataStore();
        LogEventDataStore analyticsDataStore = this.logEventDataStoreFactory.createAnalyticsDataStore();
        return Observable.merge(
                amplitudeDataStore.initialize().subscribeOn(Schedulers.io()),
                analyticsDataStore.initialize().subscribeOn(Schedulers.io()));
    }
}
