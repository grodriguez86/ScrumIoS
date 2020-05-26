package ar.edu.uade.scrumgame.domain.interactor;


import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LocalUserRepository;
import io.reactivex.Observable;

public class GetUserLocally extends UseCase<User, String> {

  private final LocalUserRepository localUserRepository;

  @Inject
  GetUserLocally(LocalUserRepository localUserRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.localUserRepository = localUserRepository;
  }

  @Override Observable<User> buildUseCaseObservable(String email) {
    return this.localUserRepository.getUser(email);
  }
}
