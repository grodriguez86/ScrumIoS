package ar.edu.uade.scrumgame.domain.repository;

import java.util.List;

import ar.edu.uade.scrumgame.domain.Progress;
import io.reactivex.Observable;

public interface ProgressRepository {
    Observable<Void> saveProgress(Progress progress);

    Observable<List<Progress>> getProgressList();

    Observable<Void> saveProgressList(List<Progress> progressList);

    Observable<Progress> getProgress(Integer levelCode);
}
