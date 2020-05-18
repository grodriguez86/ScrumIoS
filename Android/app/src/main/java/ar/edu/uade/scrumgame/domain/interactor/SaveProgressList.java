package ar.edu.uade.scrumgame.domain.interactor;

import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LocalProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteProgressRepository;
import io.reactivex.Observable;

/**
 * Overrides entire progress list!
 */
public class SaveProgressList extends UseCase<String, List<Progress>> {

    public static final class PROGRESS_SAVE_OUTCOMES {
        public static final String COMPLETE = "complete";
        public static final String FAILED_REMOTE = "failed remote";
    }

    private final LocalProgressRepository localProgressRepository;

    private final RemoteProgressRepository remoteProgressRepository;

    @Inject
    SaveProgressList(
            LocalProgressRepository localProgressRepository,
            RemoteProgressRepository remoteProgressRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.localProgressRepository = localProgressRepository;
        this.remoteProgressRepository = remoteProgressRepository;
    }

    @Override
    Observable<String> buildUseCaseObservable(List<Progress> progressList) {
        return Observable.create(emitter -> {
            localProgressRepository.saveProgressList(progressList).subscribe(new DefaultObserver<Void>() {
                @Override
                public void onComplete() {
                    remoteProgressRepository.saveProgressList(progressList).subscribe(new DefaultObserver<Void>() {
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
