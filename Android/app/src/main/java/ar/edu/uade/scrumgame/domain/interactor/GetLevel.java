package ar.edu.uade.scrumgame.domain.interactor;


import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
import io.reactivex.Observable;

public class GetLevel extends UseCase<Level, Integer> {

  private final LevelRepository levelRepository;

  @Inject
  GetLevel(LevelRepository levelRepository, ThreadExecutor threadExecutor,
           PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.levelRepository = levelRepository;
  }

  @Override Observable<Level> buildUseCaseObservable(Integer levelCode) {
    return this.levelRepository.levelByCode(levelCode);
  }
}
