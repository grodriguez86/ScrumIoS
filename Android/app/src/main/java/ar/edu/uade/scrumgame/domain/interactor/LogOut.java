package ar.edu.uade.scrumgame.domain.interactor;

import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.UserSessionRepository;
import io.reactivex.Observable;

import javax.inject.Inject;

public class LogOut extends UseCase<Void, Void> {

  private final UserSessionRepository userSessionRepository;

  @Inject
  LogOut(UserSessionRepository userSessionRepository, ThreadExecutor threadExecutor,
         PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.userSessionRepository = userSessionRepository;
  }

  @Override Observable<Void> buildUseCaseObservable(Void unused) {
    return userSessionRepository.logOut();
  }
}