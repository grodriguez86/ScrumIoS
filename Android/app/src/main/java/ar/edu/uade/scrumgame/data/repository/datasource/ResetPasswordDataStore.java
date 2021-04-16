package ar.edu.uade.scrumgame.data.repository.datasource;

import io.reactivex.Observable;

public interface ResetPasswordDataStore {
    Observable<Void> resetPassword(String email);
}
