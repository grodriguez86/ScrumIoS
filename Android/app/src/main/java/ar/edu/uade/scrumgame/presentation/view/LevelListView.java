package ar.edu.uade.scrumgame.presentation.view;

import java.util.Collection;

import ar.edu.uade.scrumgame.presentation.models.LevelModel;

public interface LevelListView extends LoadDataView {
    void renderLevelList(Collection<LevelModel> levelModelCollection);

    void enterLevel(LevelModel levelModel);
}
