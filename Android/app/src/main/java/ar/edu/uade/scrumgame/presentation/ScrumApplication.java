package ar.edu.uade.scrumgame.presentation;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import ar.edu.uade.scrumgame.presentation.di.components.ApplicationComponent;
import ar.edu.uade.scrumgame.presentation.di.components.DaggerApplicationComponent;
import ar.edu.uade.scrumgame.presentation.di.modules.ApplicationModule;
import io.realm.Realm;

public class ScrumApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        Realm.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
