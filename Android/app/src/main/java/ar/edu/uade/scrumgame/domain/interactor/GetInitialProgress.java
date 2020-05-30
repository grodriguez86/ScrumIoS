package ar.edu.uade.scrumgame.domain.interactor;

import ar.edu.uade.scrumgame.data.entity.LevelStatusConstants;
import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import io.reactivex.Observable;

import javax.inject.Inject;

public class GetInitialProgress extends UseCase<Progress,Void> {

  @Inject
  GetInitialProgress(ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
  }

  @Override Observable<Progress> buildUseCaseObservable(Void unused) {
    return Observable.create(emitter -> {
      Progress progress = new Progress();
      progress.setLevelId(1);
      progress.setBlocked(false);
      progress.setStatus(LevelStatusConstants.NOT_STARTED);
      progress.setTutorialCompleted(false);
      progress.setSublevelID(0);
      progress.setActualGame(0);
      emitter.onNext(progress);
      emitter.onComplete();
    });
  }
}
