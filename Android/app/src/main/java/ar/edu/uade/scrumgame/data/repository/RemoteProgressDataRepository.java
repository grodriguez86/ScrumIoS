package ar.edu.uade.scrumgame.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import ar.edu.uade.scrumgame.data.entity.mapper.ProgressEntityMapper;
import ar.edu.uade.scrumgame.data.repository.datasource.ProgressDataStoreFactory;
import ar.edu.uade.scrumgame.data.repository.datasource.RemoteProgressDataStore;
import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.repository.RemoteProgressRepository;
import io.reactivex.Observable;

@Singleton
public class RemoteProgressDataRepository implements RemoteProgressRepository {

    private ProgressEntityMapper progressEntityMapper;

    private ProgressDataStoreFactory progressDataStoreFactory;

    @Inject
    RemoteProgressDataRepository(ProgressEntityMapper progressEntityMapper, ProgressDataStoreFactory progressDataStoreFactory) {
        this.progressEntityMapper = progressEntityMapper;
        this.progressDataStoreFactory = progressDataStoreFactory;
    }

    @Override
    public Observable<Void> saveProgress(Progress progress) {
        RemoteProgressDataStore remoteProgressDataStore = this.progressDataStoreFactory.createRemoteProgressDataStore();
        ProgressEntity progressEntity = progressEntityMapper.progressToProgressEntity(progress);
        return remoteProgressDataStore.saveProgress(progressEntity);
    }
}
