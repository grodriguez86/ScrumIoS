package ar.edu.uade.scrumgame.domain.interactor;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LocalProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteProgressRepository;
import io.reactivex.Observable;

public class SaveProgress extends UseCase<String, Progress> {

    public static final class PROGRESS_SAVE_OUTCOMES {
        public static final String COMPLETE = "complete";
        public static final String FAILED_REMOTE = "failed remote";
    }

    private final LocalProgressRepository localProgressRepository;

    private final RemoteProgressRepository remoteProgressRepository;

    @Inject
    SaveProgress(
            LocalProgressRepository localProgressRepository,
            RemoteProgressRepository remoteProgressRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.localProgressRepository = localProgressRepository;
        this.remoteProgressRepository = remoteProgressRepository;
    }

    @Override
    Observable<String> buildUseCaseObservable(Progress progress) {
        return Observable.create(emitter -> {
            localProgressRepository.saveProgress(progress).subscribe(new DefaultObserver<Void>() {
                @Override
                public void onComplete() {
                    remoteProgressRepository.saveProgress(progress).subscribe(new DefaultObserver<Void>() {
                        @Override
                        public void onComplete() {
                            emitter.onNext(PROGRESS_SAVE_OUTCOMES.COMPLETE);
                            emitter.onComplete();
                        }

                        @Override
                        public void onError(Throwable exception) {
                            emitter.onNext(PROGRESS_SAVE_OUTCOMES.FAILED_REMOTE);
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
