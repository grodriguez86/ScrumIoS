package ar.edu.uade.scrumgame.data.repository.datasource;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Observable<List<ProgressEntity>> getProgressList() {
        return Observable.create(emitter -> {
            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                List<ProgressEntity> progressEntityList = realm.where(ProgressEntity.class).findAll();
                realm.commitTransaction();
                if (progressEntityList == null)
                    progressEntityList = new ArrayList<>();
                emitter.onNext(progressEntityList);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Observable<Void> saveProgressList(List<ProgressEntity> progressEntityList) {
        return Observable.create(emitter -> {
            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(ProgressEntity.class);
                realm.commitTransaction();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(progressEntityList);
                realm.commitTransaction();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Observable<ProgressEntity> getProgressEntity(Integer levelCode) {
        return Observable.create(emitter -> {
            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                ProgressEntity progressEntity = realm.where(ProgressEntity.class)
                        .equalTo("level_number", levelCode).findFirst();
                realm.commitTransaction();
                if (progressEntity == null)
                    emitter.onError(new RuntimeException("TODO: ProgressEntity not found error"));
                else {
                    emitter.onNext(progressEntity);
                    emitter.onComplete();
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
