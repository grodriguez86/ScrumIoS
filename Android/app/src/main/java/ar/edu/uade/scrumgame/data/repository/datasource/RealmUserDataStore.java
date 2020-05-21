package ar.edu.uade.scrumgame.data.repository.datasource;

import ar.edu.uade.scrumgame.data.entity.UserEntity;
import ar.edu.uade.scrumgame.data.exception.LocalUserNotFoundException;
import io.reactivex.Observable;
import io.realm.Realm;

class RealmUserDataStore implements LocalUserDataStore {
    @Override
    public Observable<Void> saveUser(UserEntity userEntity) {
        return Observable.create(emitter -> {
            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(userEntity);
                realm.commitTransaction();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Observable<UserEntity> getUser(String userEmail) {
        return Observable.create(emitter -> {
            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                UserEntity userEntity = realm.where(UserEntity.class).equalTo("mail", userEmail).findFirst();
                realm.commitTransaction();
                if (userEntity == null)
                    emitter.onError(new LocalUserNotFoundException());
                else {
                    emitter.onNext(userEntity);
                    emitter.onComplete();
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
