package ar.edu.uade.scrumgame.presentation.view;

import java.util.Collection;
import java.util.List;

import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;

public interface LevelView extends LoadDataView {

    void loadLevel(LevelModel level);

    void renderSubLevelList(Collection<SubLevelModel> subLevelModelCollection, ProgressModel progressModel);

    void enterSubLevel(String levelName, SubLevelModel subLevelModel, ProgressModel progressModel);

}
