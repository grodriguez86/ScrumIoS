package ar.edu.uade.scrumgame.domain.interactor;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.User;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LocalProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.LocalUserRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteUserRepository;
import io.reactivex.Observable;

public class SaveUser extends UseCase<String, User> {

    public static final class USER_SAVE_OUTCOMES {
        public static final String COMPLETE = "complete";
        public static final String FAILED_REMOTE = "failed remote";
    }

    private final LocalUserRepository localUserRepository;

    private final RemoteUserRepository remoteUserRepository;

    @Inject
    public SaveUser(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                    LocalUserRepository localUserRepository, RemoteUserRepository remoteUserRepository) {
        super(threadExecutor, postExecutionThread);
        this.localUserRepository = localUserRepository;
        this.remoteUserRepository = remoteUserRepository;
    }

    @Override
    Observable<String> buildUseCaseObservable(User user) {
        return Observable.create(emitter -> {
            localUserRepository.saveUser(user).subscribe(new DefaultObserver<Void>() {
                @Override
                public void onComplete() {
                    remoteUserRepository.saveUser(user).subscribe(new DefaultObserver<Void>() {
                        @Override
                        public void onComplete() {
                            emitter.onNext(USER_SAVE_OUTCOMES.COMPLETE);
                            emitter.onComplete();
                        }

                        @Override
                        public void onError(Throwable exception) {
                            emitter.onNext(USER_SAVE_OUTCOMES.FAILED_REMOTE);
                            emitter.onComplete();

                        }
                    });
                }

                @Override
                public void onError(Throwable exception) {
                    emitter.onError(exception);
                }
            });
        });
    }
}
