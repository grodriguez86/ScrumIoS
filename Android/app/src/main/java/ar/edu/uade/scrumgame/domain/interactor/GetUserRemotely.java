package ar.edu.uade.scrumgame.domain.interactor;


import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteUserRepository;
import io.reactivex.Observable;

public class GetUserRemotely extends UseCase<User, String> {

  private final RemoteUserRepository remoteUserRepository;

  @Inject
  GetUserRemotely(RemoteUserRepository remoteUserRepository, ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.remoteUserRepository = remoteUserRepository;
  }

  @Override Observable<User> buildUseCaseObservable(String email) {
    return this.remoteUserRepository.getUser(email);
  }
}
