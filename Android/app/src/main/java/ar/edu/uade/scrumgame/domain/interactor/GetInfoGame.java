package ar.edu.uade.scrumgame.domain.interactor;

import java.util.Collection;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.InfoGame;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
import io.reactivex.Observable;

public class GetInfoGame extends UseCase<Collection<InfoGame>, String> {

    private final LevelRepository levelRepository;

    @Inject
    GetInfoGame(LevelRepository levelRepository, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.levelRepository = levelRepository;
    }

    @Override
    Observable<Collection<InfoGame>> buildUseCaseObservable(String subLevelCode) {
        return this.levelRepository.getInfoGameBySubLevelCode(subLevelCode);
    }
}
