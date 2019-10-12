package ar.edu.uade.scrumgame.data.repository.datasource;

import java.util.List;

import ar.edu.uade.scrumgame.data.entity.LevelEntity;
import io.reactivex.Observable;

public interface LevelDataStore {
    Observable<List<LevelEntity>> levelList();
}
