package ar.edu.uade.scrumgame.data.repository.datasource;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import io.reactivex.Observable;

public interface ProgressDataStore {
    Observable<Void> saveProgress(ProgressEntity progressEntity);
}
