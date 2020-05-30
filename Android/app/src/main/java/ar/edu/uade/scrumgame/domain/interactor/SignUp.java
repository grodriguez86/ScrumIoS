package ar.edu.uade.scrumgame.domain.interactor;

import ar.edu.uade.scrumgame.domain.UserCredentials;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.SignUpRepository;
import io.reactivex.Observable;

import javax.inject.Inject;

public class SignUp extends UseCase<Void, UserCredentials> {
    private final SignUpRepository signUpRepository;

    @Inject
    public SignUp(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SignUpRepository signUpRepository) {
        super(threadExecutor, postExecutionThread);
        this.signUpRepository = signUpRepository;
    }

    @Override
    Observable<Void> buildUseCaseObservable(UserCredentials userCredentials) {
        return this.signUpRepository.signUp(userCredentials.getEmail(),userCredentials.getPassword());
    }
}
