package ar.edu.uade.scrumgame.domain.interactor;

import ar.edu.uade.scrumgame.data.exception.LocalUserNotFoundException;
import ar.edu.uade.scrumgame.domain.exception.UserAlreadyRegisteredException;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LocalUserRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteUserRepository;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CheckIncompleteSignUp extends UseCase<String, String> {
    private static final String HAS_NOT_SIGNED_UP_REMOTELY = "noRemoteSignUp";
    private static final String HAS_NOT_SIGNED_UP_LOCALLY = "noLocalSignUp";
    private static final String HAS_SIGNED_UP_REMOTELY = "remoteSignUp";
    private static final String HAS_SIGNED_UP_LOCALLY = "localSignUp";
    private static final List<String> RETRY_SIGN_UP_CASES = new LinkedList<>(Arrays.asList(HAS_NOT_SIGNED_UP_REMOTELY, HAS_SIGNED_UP_LOCALLY));
    private static final List<String> CONTINUE_SIGN_UP_CASES = new LinkedList<>(Arrays.asList(HAS_NOT_SIGNED_UP_REMOTELY, HAS_NOT_SIGNED_UP_LOCALLY));
    public static final String RETRY_SIGN_UP = "retrySignUp";
    public static final String CONTINUE_SIGN_UP = "continueSignUp";
    private final LocalUserRepository localUserRepository;
    private final RemoteUserRepository remoteUserRepository;


    @Inject
    CheckIncompleteSignUp(LocalUserRepository localUserRepository, RemoteUserRepository remoteUserRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.localUserRepository = localUserRepository;
        this.remoteUserRepository = remoteUserRepository;
    }

    @Override
    Observable<String> buildUseCaseObservable(String email) {
        Observable<String> localUserObservable = localUserRepository.getUser(email)
                .map(user -> HAS_SIGNED_UP_LOCALLY)
                .onErrorResumeNext(throwable -> throwable instanceof LocalUserNotFoundException ?
                        Observable.just(HAS_NOT_SIGNED_UP_LOCALLY) :
                        Observable.error(throwable));
        Observable<String> remoteUserObservable =
                remoteUserRepository.getUser(email)
                        .map(user -> user.getUid() == null ? HAS_NOT_SIGNED_UP_REMOTELY : HAS_SIGNED_UP_REMOTELY);
        return localUserObservable.mergeWith(remoteUserObservable)
                .toList()
                .toObservable()
                .map(values -> {
                    if (values.containsAll(RETRY_SIGN_UP_CASES)) {
                        return RETRY_SIGN_UP;
                    }
                    if (values.containsAll(CONTINUE_SIGN_UP_CASES)) {
                        return CONTINUE_SIGN_UP;
                    }
                    if(values.contains(HAS_SIGNED_UP_REMOTELY)) {
                        throw new UserAlreadyRegisteredException();
                    }
                    return CONTINUE_SIGN_UP;
                });
    }
}
