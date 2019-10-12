package ar.edu.uade.scrumgame.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LevelDataStoreFactory {
    private Context context;

    @Inject
    public LevelDataStoreFactory(Context context) {
        this.context = context;
    }

    public LevelDataStore createLocalDataStore() {
        return new LocalLevelDataStore(context);
    }
}
