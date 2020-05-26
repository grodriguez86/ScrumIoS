package ar.edu.uade.scrumgame.data.repository;

import ar.edu.uade.scrumgame.data.entity.UserEntity;
import ar.edu.uade.scrumgame.data.entity.mapper.UserEntityMapper;
import ar.edu.uade.scrumgame.data.exception.NoLoggedInUserException;
import ar.edu.uade.scrumgame.data.repository.datasource.LocalUserDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.RemoteUserDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.UserDataStoreFactory;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.repository.UserSessionRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserSessionDataRepository implements UserSessionRepository {

    private UserEntityMapper userEntityMapper;

    private UserDataStoreFactory userDataStoreFactory;

    @Inject
    public UserSessionDataRepository(UserEntityMapper userEntityMapper, UserDataStoreFactory userDataStoreFactory) {
        this.userEntityMapper = userEntityMapper;
        this.userDataStoreFactory = userDataStoreFactory;
    }

    @Override
    public Observable<User> getLoggedInUser() {
        LocalUserDataStore localUserDataStore = this.userDataStoreFactory.createLocalUserDataStore();
        RemoteUserDataStore remoteUserDataStore = this.userDataStoreFactory.createRemoteUserDataStore();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.getEmail() != null) {
            return Observable
                    .just(user.getEmail())
                    .switchMap((Function<String, Observable<UserEntity>>) s -> remoteUserDataStore.getUser(user.getEmail()))
                    .switchMap((Function<UserEntity, Observable<User>>) remoteUser -> {
                        if (remoteUser.getUid() == null) {
                            return Observable.create(emitter -> emitter.onError(new NoLoggedInUserException()));
                        }
                        return localUserDataStore.getUser(remoteUser.getMail()).map(userEntityMapper::userEntityToUser);
            });
        }
        return Observable.create(emitter -> emitter.onError(new NoLoggedInUserException()));
    }

    public Observable<User> getLocalLoggedInUser() {
        LocalUserDataStore localUserDataStore = this.userDataStoreFactory.createLocalUserDataStore();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.getEmail() != null) {
            return localUserDataStore.getUser(user.getEmail()).map(userEntityMapper::userEntityToUser);
        }
        return Observable.create(emitter -> emitter.onError(new NoLoggedInUserException()));
    }

    @Override
    public Observable<Void> logOut() {
        return Observable.create(emitter -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            emitter.onComplete();
        });
    }
}


