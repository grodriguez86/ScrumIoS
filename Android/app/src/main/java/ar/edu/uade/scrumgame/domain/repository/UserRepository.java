package ar.edu.uade.scrumgame.domain.repository;

import ar.edu.uade.scrumgame.domain.User;
import io.reactivex.Observable;

public interface UserRepository {
    Observable<Void> saveUser(User user);

    Observable<User> getUser(String userEmail);
}
