package ar.edu.uade.scrumgame.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import ar.edu.uade.scrumgame.data.entity.mapper.ProgressEntityMapper;
import ar.edu.uade.scrumgame.data.repository.datasource.LocalProgressDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.ProgressDataStoreFactory;
import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.repository.LocalProgressRepository;
import io.reactivex.Observable;

@Singleton
public class LocalProgressDataRepository implements LocalProgressRepository {

    private ProgressEntityMapper progressEntityMapper;

    private ProgressDataStoreFactory progressDataStoreFactory;

    @Inject
    LocalProgressDataRepository(ProgressDataStoreFactory progressDataStoreFactory, ProgressEntityMapper progressEntityMapper) {
        this.progressEntityMapper = progressEntityMapper;
        this.progressDataStoreFactory = progressDataStoreFactory;
    }

    @Override
    public Observable<Void> saveProgress(Progress progress) {
        LocalProgressDataStore localProgressDataStore = this.progressDataStoreFactory.createLocalProgressDataStore();
        ProgressEntity progressEntity = progressEntityMapper.progressToProgressEntity(progress);
        return localProgressDataStore.saveProgress(progressEntity);
    }

}


