package ar.edu.uade.scrumgame.presentation.di.components;

import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.di.modules.ActivityModule;
import ar.edu.uade.scrumgame.presentation.di.modules.LevelModule;
import ar.edu.uade.scrumgame.presentation.view.fragment.InfoGameFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.InfoTheoryFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.LevelFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.MenuFragment;
import ar.edu.uade.scrumgame.presentation.view.fragment.level.Level1SubLevel1Game1Fragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LevelModule.class})
public interface LevelComponent extends ActivityComponent {
    void inject(MenuFragment menuFragment);

    void inject(LevelFragment levelFragment);

    void inject(InfoTheoryFragment infoTheoryFragment);

    void inject(InfoGameFragment infoGameFragment);

    void inject(Level1SubLevel1Game1Fragment level1Sublevel1Game1Fragment);
}
