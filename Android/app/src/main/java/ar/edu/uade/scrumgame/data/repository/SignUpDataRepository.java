package ar.edu.uade.scrumgame.data.repository;

import ar.edu.uade.scrumgame.data.repository.datasource.SignUpDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.SignUpDataStoreFactory;
import ar.edu.uade.scrumgame.domain.repository.SignUpRepository;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SignUpDataRepository implements SignUpRepository {
    private SignUpDataStoreFactory signUpDataStoreFactory;

    @Inject
    public SignUpDataRepository(SignUpDataStoreFactory signUpDataStoreFactory) {
        this.signUpDataStoreFactory = signUpDataStoreFactory;
    }

    @Override
    public Observable<Void> signUp(String email, String password) {
        SignUpDataStore signUpDataStore = this.signUpDataStoreFactory.createRemoteProgressDataStore();
        return signUpDataStore.signUp(email, password);
    }
}


