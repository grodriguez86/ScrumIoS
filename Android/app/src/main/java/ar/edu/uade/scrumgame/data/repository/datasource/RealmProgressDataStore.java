package ar.edu.uade.scrumgame.data.repository.datasource;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import io.reactivex.Observable;
import io.realm.Realm;

class RealmProgressDataStore implements LocalProgressDataStore {
    @Override
    public Observable<Void> saveProgress(ProgressEntity progressEntity) {
        return Observable.create(emitter -> {
            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(progressEntity);
                realm.commitTransaction();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
