package ar.edu.uade.scrumgame.presentation;

import android.app.Application;

import ar.edu.uade.scrumgame.presentation.di.components.ApplicationComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerApplicationComponent;
import ar.edu.uade.scrumgame.presentation.di.modules.ApplicationModule;

public class ScrumApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
