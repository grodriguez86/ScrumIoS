package ar.edu.uade.scrumgame.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.repository.datasource.ResetPasswordDataStoreFactory;
import ar.edu.uade.scrumgame.domain.repository.ResetPasswordRepository;
import io.reactivex.Observable;

@Singleton
public class ResetPasswordDataRepository implements ResetPasswordRepository {
    private ResetPasswordDataStoreFactory resetPasswordDataStoreFactory;

    @Inject
    ResetPasswordDataRepository(ResetPasswordDataStoreFactory resetPasswordDataStoreFactory) {
        this.resetPasswordDataStoreFactory = resetPasswordDataStoreFactory;
    }

    @Override
    public Observable<Void> resetPassword(String email) {
        return this.resetPasswordDataStoreFactory.createFirebaseAuthDataStore().resetPassword(email);
    }

}
