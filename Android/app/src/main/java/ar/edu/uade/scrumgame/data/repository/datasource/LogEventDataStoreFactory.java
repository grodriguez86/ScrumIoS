package ar.edu.uade.scrumgame.data.repository.datasource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LogEventDataStoreFactory {
    @Inject
    public LogEventDataStoreFactory(){
    }

    public LogEventDataStore createAmplitudeDataStore() {
        return new AmplitudeDataStore();
    }
}
