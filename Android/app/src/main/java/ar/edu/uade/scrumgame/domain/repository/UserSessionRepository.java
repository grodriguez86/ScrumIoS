package ar.edu.uade.scrumgame.domain.repository;

import ar.edu.uade.scrumgame.domain.User;
import io.reactivex.Observable;

public interface UserSessionRepository {

    Observable<User> getLoggedInUser();

    Observable<Void> logOut();
}
