package ar.edu.uade.scrumgame.domain.interactor;


import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.SubLevel;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
import io.reactivex.Observable;

public class GetSubLevel extends UseCase<SubLevel, String> {

  private final LevelRepository levelRepository;

  @Inject
  GetSubLevel(LevelRepository levelRepository, ThreadExecutor threadExecutor,
              PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.levelRepository = levelRepository;
  }

  @Override Observable<SubLevel> buildUseCaseObservable(String subLevelCode) {
    return this.levelRepository.subLevelByCode(subLevelCode);
  }
}
