package ar.edu.uade.scrumgame.presentation.di.components;

import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.di.modules.ActivityModule;
import ar.edu.uade.scrumgame.presentation.di.modules.LevelModule;
import ar.edu.uade.scrumgame.presentation.view.fragment.MenuFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LevelModule.class})
public interface LevelComponent extends ActivityComponent {
    void inject(MenuFragment menuFragment);
}
