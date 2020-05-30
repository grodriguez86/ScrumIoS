package ar.edu.uade.scrumgame.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.UserEntity;
import ar.edu.uade.scrumgame.data.entity.mapper.UserEntityMapper;
import ar.edu.uade.scrumgame.data.repository.datasource.LocalUserDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.UserDataStoreFactory;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.repository.LocalUserRepository;
import io.reactivex.Observable;

@Singleton
public class LocalUserDataRepository implements LocalUserRepository {

    private UserEntityMapper userEntityMapper;

    private UserDataStoreFactory userDataStoreFactory;

    @Inject
    public LocalUserDataRepository(UserEntityMapper userEntityMapper, UserDataStoreFactory userDataStoreFactory) {
        this.userEntityMapper = userEntityMapper;
        this.userDataStoreFactory = userDataStoreFactory;
    }

    @Override
    public Observable<Void> saveUser(User user) {
        LocalUserDataStore localUserDataStore = this.userDataStoreFactory.createLocalUserDataStore();
        UserEntity userEntity = userEntityMapper.userToUserEntity(user);
        return localUserDataStore.saveUser(userEntity);
    }

    @Override
    public Observable<User> getUser(String userEmail) {
        LocalUserDataStore localUserDataStore = this.userDataStoreFactory.createLocalUserDataStore();
        return localUserDataStore.getUser(userEmail).map(userEntityMapper::userEntityToUser);
    }
}


