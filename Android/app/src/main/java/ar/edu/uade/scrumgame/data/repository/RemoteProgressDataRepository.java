package ar.edu.uade.scrumgame.data.repository;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Observable<List<Progress>> getProgressList() {
        RemoteProgressDataStore remoteProgressDataStore = this.progressDataStoreFactory.createRemoteProgressDataStore();
        return remoteProgressDataStore.getProgressList().map(progressEntityList -> {
            List<Progress> progressList = new ArrayList<>();
            for (ProgressEntity progressEntity : progressEntityList)
                progressList.add(progressEntityMapper.progressEntityToProgress(progressEntity));
            return progressList;
        });
    }

    @Override
    public Observable<Void> saveProgressList(List<Progress> progressList) {
        RemoteProgressDataStore remoteProgressDataStore = this.progressDataStoreFactory.createRemoteProgressDataStore();
        List<ProgressEntity> progressEntityList = new ArrayList<>();
        for (Progress progress: progressList)
            progressEntityList.add(progressEntityMapper.progressToProgressEntity(progress));
        return remoteProgressDataStore.saveProgressList(progressEntityList);
    }

    @Override
    public Observable<Progress> getProgress(Integer levelCode) {
        throw new RuntimeException("NOT IMPLEMENTED"); // TODO
    }
}
