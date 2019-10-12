package ar.edu.uade.scrumgame.presentation.di.modules;

import android.app.Activity;

import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
