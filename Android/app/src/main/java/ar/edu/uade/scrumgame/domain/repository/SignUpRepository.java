package ar.edu.uade.scrumgame.domain.repository;

import io.reactivex.Observable;

public interface SignUpRepository {

    Observable<Void> signUp(String email, String password);
}
