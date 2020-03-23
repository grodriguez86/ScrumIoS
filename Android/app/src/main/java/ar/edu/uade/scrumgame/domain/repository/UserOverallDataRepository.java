package ar.edu.uade.scrumgame.domain.repository;

import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.UserOverallData;
import io.reactivex.Observable;

public interface UserOverallDataRepository {
    Observable<Void> saveUserOverallData(UserOverallData userOverallData);
}
