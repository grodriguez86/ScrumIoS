package ar.edu.uade.scrumgame.domain.interactor;

import java.util.Collection;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.InfoGame;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
import io.reactivex.Observable;
import io.reactivex.Observer;

public class SaveProgress extends UseCase<Integer, String> {

    private final LevelRepository levelRepository;

    @Inject
    SaveProgress(LevelRepository levelRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.levelRepository = levelRepository;
    }

    @Override
    Observable<Integer> buildUseCaseObservable(String subLevelCode) {
        //TODO guardar progreso y devolver porcentaje completado para el nivel actual
        return new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {

            }
        };
    }
}
