package ar.edu.uade.scrumgame.domain.interactor;


import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LocalProgressRepository;
import io.reactivex.Observable;

public class GetProgressListLocally extends UseCase<List<Progress>, Void> {

    private final LocalProgressRepository localProgressRepository;

    @Inject
    public GetProgressListLocally(ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread,
                                  LocalProgressRepository localProgressRepository) {
        super(threadExecutor, postExecutionThread);
        this.localProgressRepository = localProgressRepository;
    }

    @Override
    Observable<List<Progress>> buildUseCaseObservable(Void nothing) {
            return localProgressRepository.getProgressList();
    }
}
