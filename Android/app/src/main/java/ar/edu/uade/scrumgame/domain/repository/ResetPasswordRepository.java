package ar.edu.uade.scrumgame.domain.repository;

import io.reactivex.Observable;

public interface ResetPasswordRepository {

    Observable<Void> resetPassword(String email);
}
