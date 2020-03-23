package ar.edu.uade.scrumgame.domain.repository;

import ar.edu.uade.scrumgame.domain.Progress;
import io.reactivex.Observable;

public interface ProgressRepository {
    Observable<Void> saveProgress(Progress progress);
}
