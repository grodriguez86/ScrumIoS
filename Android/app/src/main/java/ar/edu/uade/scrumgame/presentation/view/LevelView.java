package ar.edu.uade.scrumgame.presentation.view;

import java.util.Collection;

import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;

public interface LevelView extends LoadDataView {

    void loadLevel(LevelModel level);

    void renderSubLevelList(Collection<SubLevelModel> subLevelModelCollection);

    void enterSubLevel(String levelName, SubLevelModel subLevelModel);
}
