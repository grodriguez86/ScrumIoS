package ar.edu.uade.scrumgame.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LogEventDataStoreFactory {
    private Context context;

    @Inject
    public LogEventDataStoreFactory(Context context) {
        this.context = context;
    }

    public LogEventDataStore createAmplitudeDataStore() {
        return new AmplitudeDataStore(this.context);
    }

    public LogEventDataStore createAnalyticsDataStore() {
        return new AnalyticsDataStore(this.context);
    }
}
