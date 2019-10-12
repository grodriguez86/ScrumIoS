package ar.edu.uade.scrumgame.presentation.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.GameContent;
import ar.edu.uade.scrumgame.domain.InfoGame;
import ar.edu.uade.scrumgame.domain.InfoTheory;
import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.SubLevel;
import ar.edu.uade.scrumgame.domain.ViewType;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;
import ar.edu.uade.scrumgame.presentation.models.InfoTheoryModel;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import ar.edu.uade.scrumgame.presentation.models.ViewTypeModel;

@PerActivity
public class LevelModelDataMapper {

    @Inject
    LevelModelDataMapper() {
    }

    private LevelModel transform(Level level) {
        if (level == null) {
            throw new IllegalArgumentException("Level can't be null");
        }
        return convertLevelToLevelModel(level);
    }

    private LevelModel convertLevelToLevelModel(Level level) {
        LevelModel levelModel = new LevelModel();
        levelModel.setCode(level.getCode());
        levelModel.setName(level.getName());
        levelModel.setSublevels(convertSubLevelToSubLevelModel(level.getSublevels()));
        return levelModel;
    }

    private List<SubLevelModel> convertSubLevelToSubLevelModel(List<SubLevel> subLevelList) {
        List<SubLevelModel> subLevels = new LinkedList<>();
        if (subLevelList != null) {
            for (SubLevel subLevel : subLevelList) {
                subLevels.add(convertSubLevelEntityToSubLevelModel(subLevel));
            }
        }
        return subLevels;
    }

    private SubLevelModel convertSubLevelEntityToSubLevelModel(SubLevel subLevel) {
        SubLevelModel subLevelModel = new SubLevelModel();
        subLevelModel.setCode(subLevel.getCode());
        subLevelModel.setName(subLevel.getName());
        subLevelModel.setInfoGame(convertInfoGameToInfoGameModel(subLevel.getInfoGame()));
        subLevelModel.setInfoTheory(convertInfoTheoryToInfoTheoryModel(subLevel.getInfoTheory()));
        return subLevelModel;
    }

    private List<InfoTheoryModel> convertInfoTheoryToInfoTheoryModel(List<InfoTheory> infoTheoryList) {
        List<InfoTheoryModel> infoTheoryModelList = new LinkedList<>();
        if (infoTheoryList != null) {
            for (InfoTheory infoTheory : infoTheoryList) {
                infoTheoryModelList.add(convertInfoTheoryToInfoTheoryModel(infoTheory));
            }
        }
        return infoTheoryModelList;
    }

    private InfoTheoryModel convertInfoTheoryToInfoTheoryModel(InfoTheory infoTheory) {
        InfoTheoryModel infoTheoryModel = new InfoTheoryModel();
        infoTheoryModel.setCode(infoTheory.getCode());
        infoTheoryModel.setInfoTheory(convertViewTypeToViewTypeModel(infoTheory.getInfoTheory()));
        return infoTheoryModel;
    }

    private List<ViewTypeModel> convertViewTypeToViewTypeModel(List<ViewType> viewTypeList) {
        List<ViewTypeModel> viewTypeModelList = new LinkedList<>();
        if (viewTypeList != null) {
            for (ViewType viewType : viewTypeList) {
                viewTypeModelList.add(convertViewTypeToViewTypeModel(viewType));
            }
        }
        return viewTypeModelList;
    }

    private ViewTypeModel convertViewTypeToViewTypeModel(ViewType viewType) {
        ViewTypeModel viewTypeModel = new ViewTypeModel();
        viewTypeModel.setData(viewType.getData());
        viewTypeModel.setType(viewType.getType());
        return viewTypeModel;
    }

    private List<InfoGameModel> convertInfoGameToInfoGameModel(List<InfoGame> infoGameList) {
        List<InfoGameModel> infoGames = new LinkedList<>();
        if (infoGameList != null) {
            for (InfoGame infoGame : infoGameList) {
                infoGames.add(convertInfoGameToInfoGameModel(infoGame));
            }
        }
        return infoGames;
    }

    private InfoGameModel convertInfoGameToInfoGameModel(InfoGame infoGame) {
        InfoGameModel infoGameModel = new InfoGameModel();
        infoGameModel.setCode(infoGame.getCode());
        infoGameModel.setType(infoGame.getType());
        infoGameModel.setContent(convertGameToGameModel(infoGame.getContent()));
        return infoGameModel;
    }

    private List<GameContentModel> convertGameToGameModel(List<GameContent> content) {
        List<GameContentModel> gameContentModel = new LinkedList<>();
        if (content != null) {
            for (GameContent gameContent : content) {
                gameContentModel.add(convertGameToGameModel(gameContent));
            }
        }
        return gameContentModel;
    }

    private GameContentModel convertGameToGameModel(GameContent gameContent) {
        GameContentModel gameContentModel = new GameContentModel();
        gameContentModel.setCorrect(gameContent.getCorrect());
        gameContentModel.setData(gameContent.getData());
        gameContentModel.setType(gameContent.getType());
        return gameContentModel;
    }

    public Collection<LevelModel> transform(Collection<Level> levelCollection) {
        Collection<LevelModel> levelModelCollection;
        if (levelCollection != null && !levelCollection.isEmpty()) {
            levelModelCollection = new LinkedList<>();
            for (Level level : levelCollection) {
                levelModelCollection.add(transform(level));
            }
        } else {
            levelModelCollection = Collections.emptyList();
        }
        return levelModelCollection;
    }
}
