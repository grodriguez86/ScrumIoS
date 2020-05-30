package ar.edu.uade.scrumgame.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.UserOverallDataEntity;
import ar.edu.uade.scrumgame.data.entity.mapper.UserOverallDataEntityMapper;
import ar.edu.uade.scrumgame.data.repository.datasource.UserOverallDataDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.UserOverallDataDataStoreFactory;
import ar.edu.uade.scrumgame.domain.UserOverallData;
import ar.edu.uade.scrumgame.domain.repository.UserOverallDataRepository;
import io.reactivex.Observable;

@Singleton
public class UserOverallDataDataRepository implements UserOverallDataRepository {

    private UserOverallDataEntityMapper userOverallDataEntityMapper;

    private UserOverallDataDataStoreFactory userOverallDataDataStoreFactory;

    @Inject
    public UserOverallDataDataRepository(UserOverallDataEntityMapper userOverallDataEntityMapper,
                                         UserOverallDataDataStoreFactory userOverallDataDataStoreFactory) {
        this.userOverallDataEntityMapper = userOverallDataEntityMapper;
        this.userOverallDataDataStoreFactory = userOverallDataDataStoreFactory;
    }

    @Override
    public Observable<Void> saveUserOverallData(UserOverallData userOverallData) {
        UserOverallDataDataStore userOverallDataDataStore = this.userOverallDataDataStoreFactory
                .createUserOverallDataDataStore();
        UserOverallDataEntity userOverallDataEntity = userOverallDataEntityMapper
                .userOverallDataToUserOverallDataEntity(userOverallData);
        return userOverallDataDataStore.saveUserOverallData(userOverallDataEntity);
    }

    @Override
    public Observable<UserOverallData> getUserOverallData() {
        UserOverallDataDataStore userOverallDataDataStore = this.userOverallDataDataStoreFactory
                .createUserOverallDataDataStore();
        return userOverallDataDataStore.getUserOverallData()
                .map(userOverallDataEntityMapper::userOverallDataEntityToUserOverallData);
    }
}


