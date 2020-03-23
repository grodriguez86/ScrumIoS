package ar.edu.uade.scrumgame.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserOverallDataDataStoreFactory {
    private Context context;

    @Inject
    public UserOverallDataDataStoreFactory(Context context) {
        this.context = context;
    }

    public UserOverallDataDataStore createUserOverallDataDataStore() {
        return new RealmOverallDataDataStore();
    }

}
