package ar.edu.uade.scrumgame.data.repository.datasource;

import io.reactivex.Observable;

public interface SignUpDataStore {
    Observable<Void> signUp(String email, String password);
}
