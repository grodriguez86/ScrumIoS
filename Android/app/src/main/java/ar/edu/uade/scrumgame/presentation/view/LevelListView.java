package ar.edu.uade.scrumgame.presentation.view;

import java.util.Collection;

import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.UserOverallDataModel;

public interface LevelListView extends LoadDataView {
    void renderLevelList(Collection<LevelModel> levelModelCollection,
                         Collection<ProgressModel> progressModelCollection,
                         UserOverallDataModel userOverallDataModel);

    void enterLevel(LevelModel levelModel);
}
