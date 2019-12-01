package ar.edu.uade.scrumgame.presentation.view;

import java.util.Collection;

import ar.edu.uade.scrumgame.presentation.models.InfoTheoryModel;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;

public interface InfoTheoryView extends LoadDataView {

    void loadSubLevel(SubLevelModel subLevelModel);

    void renderInfoTheoryList(Collection<InfoTheoryModel> infoTheoryModelCollection);

    void playSubLevel(Integer levelCode, String subLevelCode);
}
