package ar.edu.uade.scrumgame.presentation.di.components;

import android.content.Context;

import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.*;
import ar.edu.uade.scrumgame.presentation.di.modules.ApplicationModule;
import ar.edu.uade.scrumgame.presentation.view.activity.BaseActivity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    LevelRepository levelRepository();

    LocalProgressRepository localProgressRepository();

    RemoteProgressRepository remoteProgressRepository();

    LocalUserRepository localUserRepository();

    RemoteUserRepository remoteUserRepository();

    UserOverallDataRepository userOverallDataRepository();

    SignUpRepository signUpDataRepository();

    UserSessionRepository userSessionRepository();

    LogEventRepository logEventRepository();

    ResetPasswordRepository resetPasswordRepository();
}
