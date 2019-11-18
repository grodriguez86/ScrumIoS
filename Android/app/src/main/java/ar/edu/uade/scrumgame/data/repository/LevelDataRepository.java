package ar.edu.uade.scrumgame.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.mapper.LevelEntityDataMapper;
import ar.edu.uade.scrumgame.data.repository.datasource.LevelDataStore;
import ar.edu.uade.scrumgame.data.repository.datasource.LevelDataStoreFactory;
import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.SubLevel;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
import io.reactivex.Observable;

@Singleton
public class LevelDataRepository implements LevelRepository {
    private LevelDataStoreFactory levelDataStoreFactory;
    private LevelEntityDataMapper levelEntityDataMapper;

    @Inject
    LevelDataRepository(LevelDataStoreFactory levelDataStoreFactory, LevelEntityDataMapper levelEntityDataMapper) {
        this.levelDataStoreFactory = levelDataStoreFactory;
        this.levelEntityDataMapper = levelEntityDataMapper;
    }

    @Override
    public Observable<List<Level>> levels() {
        LevelDataStore levelDataStore = levelDataStoreFactory.createLocalDataStore();
        return levelDataStore.levelList().map(this.levelEntityDataMapper::convert);
    }

    @Override
    public Observable<Level> levelByCode(Integer code) {
        LevelDataStore levelDataStore = levelDataStoreFactory.createLocalDataStore();
        return levelDataStore.levelByCode(code).map(this.levelEntityDataMapper::convertLevelEntityToLevel);
    }

    @Override
    public Observable<SubLevel> subLevelByCode(String code) {
        LevelDataStore levelDataStore = levelDataStoreFactory.createLocalDataStore();
        return levelDataStore.subLevelByCode(code).map(this.levelEntityDataMapper::convertSubLevelEntityToSubLevel);
    }
}
