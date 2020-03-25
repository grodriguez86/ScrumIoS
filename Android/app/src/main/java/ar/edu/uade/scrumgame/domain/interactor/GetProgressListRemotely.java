package ar.edu.uade.scrumgame.domain.interactor;


import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.data.entity.LevelStatusConstants;
import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.domain.UserOverallData;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LocalProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.UserOverallDataRepository;
import io.reactivex.Observable;

public class GetProgressListRemotely extends UseCase<List<Progress>, Void> {

    private final RemoteProgressRepository remoteProgressRepository;

    private final LocalProgressRepository localProgressRepository;

    private final UserOverallDataRepository userOverallDataRepository;

    @Inject
    public GetProgressListRemotely(ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread,
                                   RemoteProgressRepository remoteProgressRepository,
                                   LocalProgressRepository localProgressRepository,
                                   UserOverallDataRepository userOverallDataRepository) {
        super(threadExecutor, postExecutionThread);
        this.remoteProgressRepository = remoteProgressRepository;
        this.localProgressRepository = localProgressRepository;
        this.userOverallDataRepository = userOverallDataRepository;
    }

    @Override
    Observable<List<Progress>> buildUseCaseObservable(Void nothing) {
        try {
            return Observable.create(emitter -> {
                remoteProgressRepository.getProgressList().subscribe(new DefaultObserver<List<Progress>>() {
                    @Override
                    public void onNext(List<Progress> fetchedProgressList) {
                        localProgressRepository.saveProgressList(fetchedProgressList).subscribe(new DefaultObserver<Void>() {
                            @Override
                            public void onComplete() {
                                int levelNumber = 1;
                                for (
                                        Progress progress : fetchedProgressList)
                                    if (!progress.getStatus().

                                            equals(LevelStatusConstants.NOT_STARTED))
                                        levelNumber = progress.getLevelId();
                                UserOverallData userOverallData = new UserOverallData(levelNumber);
                                userOverallDataRepository.saveUserOverallData(userOverallData).subscribe(new DefaultObserver<Void>() {
                                    @Override
                                    public void onComplete() {
                                        emitter.onNext(fetchedProgressList);
                                        emitter.onComplete();
                                    }

                                    @Override
                                    public void onError(Throwable exception) {
                                        emitter.onError(exception);
                                    }
                                });
                            }

                            @Override
                            public void onError(Throwable exception) {
                                emitter.onError(exception);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable exception) {
                        emitter.onError(exception);
                    }
                });
            });
        } catch (Exception e) {
            throw e;
        }
    }
}
