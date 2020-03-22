package ar.edu.uade.scrumgame.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.UserEntity;
import ar.edu.uade.scrumgame.data.entity.mapper.UserEntityMapper;
import ar.edu.uade.scrumgame.data.repository.datasource.RemoteUserDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.UserDataStoreFactory;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.repository.RemoteUserRepository;
import io.reactivex.Observable;

@Singleton
public class RemoteUserDataRepository implements RemoteUserRepository {

    private UserEntityMapper userEntityMapper;

    private UserDataStoreFactory userDataStoreFactory;

    @Inject
    public RemoteUserDataRepository(UserEntityMapper userEntityMapper, UserDataStoreFactory userDataStoreFactory) {
        this.userEntityMapper = userEntityMapper;
        this.userDataStoreFactory = userDataStoreFactory;
    }

    @Override
    public Observable<Void> saveUser(User user) {
        RemoteUserDataStore remoteUserDataStore = this.userDataStoreFactory.createRemoteUserDataStore();
        UserEntity userEntity = userEntityMapper.userToUserEntity(user);
        return remoteUserDataStore.saveUser(userEntity);
    }
}


