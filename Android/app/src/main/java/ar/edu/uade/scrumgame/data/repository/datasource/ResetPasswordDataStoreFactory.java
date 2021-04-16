package ar.edu.uade.scrumgame.data.repository.datasource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResetPasswordDataStoreFactory {
    @Inject
    public ResetPasswordDataStoreFactory() {
    }

    public ResetPasswordDataStore createFirebaseAuthDataStore() {
        return new FirebaseAuthDataStore();
    }
}
