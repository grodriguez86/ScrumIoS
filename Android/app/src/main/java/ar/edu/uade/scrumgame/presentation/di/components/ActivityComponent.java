package ar.edu.uade.scrumgame.presentation.di.components;

import android.app.Activity;

import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.di.modules.ActivityModule;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
interface ActivityComponent {

    Activity activity();
}
