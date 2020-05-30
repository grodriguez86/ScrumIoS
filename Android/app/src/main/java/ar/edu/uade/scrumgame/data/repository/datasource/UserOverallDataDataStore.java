package ar.edu.uade.scrumgame.data.repository.datasource;

import ar.edu.uade.scrumgame.data.entity.UserOverallDataEntity;
import ar.edu.uade.scrumgame.domain.UserOverallData;
import io.reactivex.Observable;

public interface UserOverallDataDataStore {
    Observable<Void> saveUserOverallData(UserOverallDataEntity userOverallDataEntity);

    Observable<UserOverallDataEntity> getUserOverallData();
}
