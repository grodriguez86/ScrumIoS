package ar.edu.uade.scrumgame.domain.interactor;

import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.UserSessionRepository;
import io.reactivex.Observable;

import javax.inject.Inject;

public class GetLoggedInUser extends UseCase<User, Boolean> {

    private final UserSessionRepository userSessionRepository;

    @Inject
    GetLoggedInUser(UserSessionRepository userSessionRepository, ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    Observable<User> buildUseCaseObservable(Boolean checkIfExistsOnRemote) {
        if (checkIfExistsOnRemote) {
            return userSessionRepository.getLoggedInUser();
        }
        return userSessionRepository.getLocalLoggedInUser();
    }
}
