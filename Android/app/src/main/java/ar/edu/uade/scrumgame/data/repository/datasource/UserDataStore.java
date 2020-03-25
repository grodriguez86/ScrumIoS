package ar.edu.uade.scrumgame.data.repository.datasource;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import ar.edu.uade.scrumgame.data.entity.UserEntity;
import io.reactivex.Observable;

public interface UserDataStore {
    Observable<Void> saveUser(UserEntity userEntity);

    Observable<UserEntity> getUser(String userEmail);
}
