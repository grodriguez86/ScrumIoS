package ar.edu.uade.scrumgame.domain.interactor;


import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.UserOverallData;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LocalProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.UserOverallDataRepository;
import io.reactivex.Observable;

public class GetUserOverallData extends UseCase<UserOverallData, Void> {

    private final UserOverallDataRepository userOverallDataRepository;

    @Inject
    public GetUserOverallData(ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread,
                              UserOverallDataRepository userOverallDataRepository) {
        super(threadExecutor, postExecutionThread);
        this.userOverallDataRepository = userOverallDataRepository;
    }

    @Override
    Observable<UserOverallData> buildUseCaseObservable(Void nothing) {
            return userOverallDataRepository.getUserOverallData();
    }
}
