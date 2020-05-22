package ar.edu.uade.scrumgame.domain.interactor;

import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LogEventRepository;
import io.reactivex.Observable;

import javax.inject.Inject;

public class InitializeApplication extends UseCase<Void, Void> {
    private final LogEventRepository logEventRepository;

    @Inject
    InitializeApplication(LogEventRepository logEventRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.logEventRepository = logEventRepository;
    }

    @Override
    Observable<Void> buildUseCaseObservable(Void unused) {
        return this.logEventRepository.initialize();
    }
}