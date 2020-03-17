package ar.edu.uade.scrumgame.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.ProgressEntity;
import ar.edu.uade.scrumgame.domain.Progress;

@Singleton
public class ProgressEntityMapper {
    @Inject
    public ProgressEntityMapper() {

    }

    public ProgressEntity progressToProgressEntity(Progress progress) {
        ProgressEntity progressEntity = new ProgressEntity();
        progressEntity.setSublevelID(progress.getSublevelID());
        progressEntity.setActualGame(progress.getActualGame());
        progressEntity.setTutorialCompleted(progress.isTutorialCompleted());
        progressEntity.setStatus(progress.getStatus());
        progressEntity.setBlocked(progress.isBlocked());
        progressEntity.setpK(progress.getpK());
        progressEntity.setLevelId(progress.getLevelId());
        progressEntity.setTotalGames(progress.getTotalGames());
        return progressEntity;
    }

    public Progress progressEntityToProgress(ProgressEntity progressEntity) {
        Progress progress = new Progress();
        progress.setSublevelID(progressEntity.getSublevelID());
        progress.setActualGame(progressEntity.getActualGame());
        progress.setTutorialCompleted(progressEntity.isTutorialCompleted());
        progress.setStatus(progressEntity.getStatus());
        progress.setBlocked(progressEntity.isBlocked());
        progress.setpK(progressEntity.getpK());
        progress.setLevelId(progressEntity.getLevelId());
        progress.setTotalGames(progressEntity.getTotalGames());
        return progress;
    }
}
