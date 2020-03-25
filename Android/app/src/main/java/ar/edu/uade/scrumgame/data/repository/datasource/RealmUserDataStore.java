package ar.edu.uade.scrumgame.data.repository.datasource;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import ar.edu.uade.scrumgame.data.entity.UserEntity;
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
                    emitter.onError(new RuntimeException("TODO user not found"));
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
