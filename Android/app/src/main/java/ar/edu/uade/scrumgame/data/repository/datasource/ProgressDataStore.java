package ar.edu.uade.scrumgame.data.repository.datasource;

import java.util.List;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import io.reactivex.Observable;

public interface ProgressDataStore {
    Observable<Void> saveProgress(ProgressEntity progressEntity);

    Observable<List<ProgressEntity>> getProgressList();

    Observable<Void> saveProgressList(List<ProgressEntity> progressEntityList);

    Observable<ProgressEntity> getProgressEntity(Integer levelCode);
}
