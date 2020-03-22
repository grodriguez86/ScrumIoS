package ar.edu.uade.scrumgame.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDataStoreFactory {
    private Context context;

    @Inject
    public UserDataStoreFactory(Context context) {
        this.context = context;
    }

    public LocalUserDataStore createLocalUserDataStore() {
        return new RealmUserDataStore();
    }

    public RemoteUserDataStore createRemoteUserDataStore() {
        return new FirestoreUserDataStore();
    }
}
