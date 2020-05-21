package ar.edu.uade.scrumgame.data.repository.datasource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SignUpDataStoreFactory {

    @Inject
    public SignUpDataStoreFactory() {
    }

    public SignUpDataStore createRemoteProgressDataStore() {
        return new SignUpFirebaseDataStore();
    }
}
