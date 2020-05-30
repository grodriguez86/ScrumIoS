package ar.edu.uade.scrumgame.data.repository.datasource;

import ar.edu.uade.scrumgame.data.entity.UserOverallDataEntity;
import io.reactivex.Observable;
import io.realm.Realm;

class RealmOverallDataDataStore implements UserOverallDataDataStore {
    @Override
    public Observable<Void> saveUserOverallData(UserOverallDataEntity userOverallDataEntity) {
        return Observable.create(emitter -> {
            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(userOverallDataEntity);
                realm.commitTransaction();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Observable<UserOverallDataEntity> getUserOverallData() {
        return Observable.create(emitter -> {
            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                UserOverallDataEntity userOverallDataEntity = realm.where(UserOverallDataEntity.class).findFirst();
                realm.commitTransaction();
                emitter.onNext(userOverallDataEntity);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
