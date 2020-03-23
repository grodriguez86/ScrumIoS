package ar.edu.uade.scrumgame.presentation.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.executor.JobExecutor;
import ar.edu.uade.scrumgame.data.repository.LocalProgressDataRepository;
import ar.edu.uade.scrumgame.data.repository.LocalUserDataRepository;
import ar.edu.uade.scrumgame.data.repository.RemoteProgressDataRepository;
import ar.edu.uade.scrumgame.data.repository.RemoteUserDataRepository;
import ar.edu.uade.scrumgame.data.repository.UserOverallDataDataRepository;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.data.repository.LevelDataRepository;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
import ar.edu.uade.scrumgame.domain.repository.LocalProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.LocalUserRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteProgressRepository;
import ar.edu.uade.scrumgame.domain.repository.RemoteUserRepository;
import ar.edu.uade.scrumgame.domain.repository.UserOverallDataRepository;
import ar.edu.uade.scrumgame.presentation.ScrumApplication;
import ar.edu.uade.scrumgame.presentation.UIThread;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private ScrumApplication application;

    public ApplicationModule(ScrumApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    LevelRepository provideLevelRepository(LevelDataRepository levelRepository) {
        return levelRepository;
    }

    @Provides
    @Singleton
    LocalProgressRepository provideLocalProgressRepository(LocalProgressDataRepository localProgressDataRepository) {
        return localProgressDataRepository;
    }

    @Provides
    @Singleton
    RemoteProgressRepository provideRemoteProgressRepository(RemoteProgressDataRepository remoteProgressDataRepository) {
        return remoteProgressDataRepository;
    }

    @Provides
    @Singleton
    LocalUserRepository provideLocalUserRepository(LocalUserDataRepository localUserDataRepository) {
        return localUserDataRepository;
    }

    @Provides
    @Singleton
    RemoteUserRepository provideRemoteUserRepository(RemoteUserDataRepository remoteUserDataRepository) {
        return remoteUserDataRepository;
    }

    @Provides
    @Singleton
    UserOverallDataRepository provideUserOverallDataRepository(UserOverallDataDataRepository userOverallDataDataRepository) {
        return userOverallDataDataRepository;
    }
}
