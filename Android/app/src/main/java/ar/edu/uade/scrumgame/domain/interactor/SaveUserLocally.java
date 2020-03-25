package ar.edu.uade.scrumgame.domain.interactor;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LocalUserRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteUserRepository;
import io.reactivex.Observable;

public class SaveUserLocally extends UseCase<Void, User> {

    private final LocalUserRepository localUserRepository;

    @Inject
    public SaveUserLocally(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                           LocalUserRepository localUserRepository) {
        super(threadExecutor, postExecutionThread);
        this.localUserRepository = localUserRepository;
    }

    @Override
    Observable<Void> buildUseCaseObservable(User user) {
        return localUserRepository.saveUser(user);
    }
}
