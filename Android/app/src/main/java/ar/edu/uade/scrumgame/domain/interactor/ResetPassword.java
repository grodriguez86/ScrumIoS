package ar.edu.uade.scrumgame.domain.interactor;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.ResetPasswordRepository;
import io.reactivex.Observable;

public class ResetPassword extends UseCase<Void, String> {
    private final ResetPasswordRepository resetPasswordRepository;

    @Inject
    public ResetPassword(ResetPasswordRepository resetPasswordRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.resetPasswordRepository = resetPasswordRepository;
    }

    @Override
    Observable<Void> buildUseCaseObservable(String email) {
        return this.resetPasswordRepository.resetPassword(email);
    }

}
