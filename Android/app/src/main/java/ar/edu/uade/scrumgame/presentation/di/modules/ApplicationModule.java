package ar.edu.uade.scrumgame.presentation.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.executor.JobExecutor;
import ar.edu.uade.scrumgame.domain.executor.PostExecutionThread;
import ar.edu.uade.scrumgame.domain.executor.ThreadExecutor;
import ar.edu.uade.scrumgame.data.repository.LevelDataRepository;
import ar.edu.uade.scrumgame.domain.repository.LevelRepository;
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
}
