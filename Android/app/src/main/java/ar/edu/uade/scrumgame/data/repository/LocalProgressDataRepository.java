package ar.edu.uade.scrumgame.data.repository;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Observable<List<Progress>> getProgressList() {
        LocalProgressDataStore localProgressDataStore = this.progressDataStoreFactory.createLocalProgressDataStore();
        return localProgressDataStore.getProgressList().map(progressEntityList -> {
            List<Progress> progressList = new ArrayList<>();
            for (ProgressEntity progressEntity : progressEntityList)
                progressList.add(progressEntityMapper.progressEntityToProgress(progressEntity));
            return progressList;
        });
    }

    @Override
    public Observable<Void> saveProgressList(List<Progress> progressList) {
        LocalProgressDataStore localProgressDataStore = this.progressDataStoreFactory.createLocalProgressDataStore();
        List<ProgressEntity> progressEntityList = new ArrayList<>();
        for (Progress progress : progressList)
            progressEntityList.add(progressEntityMapper.progressToProgressEntity(progress));
        return localProgressDataStore.saveProgressList(progressEntityList);
    }

    @Override
    public Observable<Progress> getProgress(Integer levelCode) {
        LocalProgressDataStore localProgressDataStore = this.progressDataStoreFactory.createLocalProgressDataStore();
        return localProgressDataStore.getProgressEntity(levelCode).map(progressEntityMapper::progressEntityToProgress);
    }
}


