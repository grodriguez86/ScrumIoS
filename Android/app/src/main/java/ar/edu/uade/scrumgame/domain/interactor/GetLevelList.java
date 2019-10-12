package ar.edu.uade.scrumgame.domain.interactor;


import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

public class GetLevelList extends UseCase<List<Level>, Void> {

  private final LevelRepository levelRepository;

  @Inject
  GetLevelList(LevelRepository levelRepository, ThreadExecutor threadExecutor,
              PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.levelRepository = levelRepository;
  }

  @Override Observable<List<Level>> buildUseCaseObservable(Void unused) {
    return this.levelRepository.levels();
  }
}
