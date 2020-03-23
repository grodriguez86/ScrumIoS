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
}
