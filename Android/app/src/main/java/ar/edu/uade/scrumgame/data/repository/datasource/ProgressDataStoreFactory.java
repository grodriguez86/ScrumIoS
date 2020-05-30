package ar.edu.uade.scrumgame.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProgressDataStoreFactory {
    private Context context;

    @Inject
    public ProgressDataStoreFactory(Context context) {
        this.context = context;
    }

    public LocalProgressDataStore createLocalProgressDataStore() {
        return new RealmProgressDataStore();
    }

    public RemoteProgressDataStore createRemoteProgressDataStore() {
        return new FirestoreProgressDataStore();
    }
}
