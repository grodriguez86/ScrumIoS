package ar.edu.uade.scrumgame.domain.interactor;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.UserOverallData;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.UserOverallDataRepository;

import io.reactivex.Observable;

public class SaveUserOverallData extends UseCase<Void, UserOverallData> {

    private final UserOverallDataRepository userOverallDataRepository;

    @Inject
    public SaveUserOverallData(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                               UserOverallDataRepository userOverallDataRepository) {
        super(threadExecutor, postExecutionThread);
        this.userOverallDataRepository = userOverallDataRepository;
    }

    @Override
    Observable<Void> buildUseCaseObservable(UserOverallData userOverallData) {
        return this.userOverallDataRepository.saveUserOverallData(userOverallData);
    }
}
