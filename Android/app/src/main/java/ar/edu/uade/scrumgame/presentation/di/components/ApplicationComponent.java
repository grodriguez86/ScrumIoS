package ar.edu.uade.scrumgame.presentation.di.components;

import android.content.Context;

import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
import ar.edu.uade.scrumgame.domain.repository.LocalProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.LocalUserRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteUserRepository;
import ar.edu.uade.scrumgame.domain.repository.UserOverallDataRepository;
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
}
