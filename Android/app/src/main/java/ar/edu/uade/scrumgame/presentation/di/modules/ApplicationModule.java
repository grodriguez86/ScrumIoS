package ar.edu.uade.scrumgame.presentation.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.executor.JobExecutor;
import ar.edu.uade.scrumgame.data.repository.*;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.domain.repository.*;
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

    @Provides
    @Singleton
    SignUpRepository provideSignUpDataRepository(SignUpDataRepository signUpDataRepository) {
        return signUpDataRepository;
    }

    @Provides
    @Singleton
    UserSessionRepository provideUserSessionDataRepository(UserSessionDataRepository userSessionDataRepository) {
        return userSessionDataRepository;
    }

    @Provides
    @Singleton
    LogEventRepository provideLogEventDataRepository(LogEventDataRepository logEventDataRepository) {
        return logEventDataRepository;
    }

    @Provides
    @Singleton
    ResetPasswordRepository provideResetPasswordDataRepository(ResetPasswordDataRepository resetPasswordDataRepository) {
        return resetPasswordDataRepository;
    }
}
