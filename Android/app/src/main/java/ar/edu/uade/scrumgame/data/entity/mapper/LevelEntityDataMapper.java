package ar.edu.uade.scrumgame.data.entity.mapper;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.scrumgame.data.entity.GameContentEntity;
import ar.edu.uade.scrumgame.data.entity.InfoGameEntity;
import ar.edu.uade.scrumgame.data.entity.InfoTheoryEntity;
import ar.edu.uade.scrumgame.data.entity.LevelEntity;
import ar.edu.uade.scrumgame.data.entity.SubLevelEntity;
import ar.edu.uade.scrumgame.data.entity.ViewTypeEntity;
import ar.edu.uade.scrumgame.domain.GameContent;
import ar.edu.uade.scrumgame.domain.InfoGame;
import ar.edu.uade.scrumgame.domain.InfoTheory;
import ar.edu.uade.scrumgame.domain.Level;
import ar.edu.uade.scrumgame.domain.SubLevel;
import ar.edu.uade.scrumgame.domain.ViewType;

@Singleton
public class LevelEntityDataMapper {

    @Inject
    public LevelEntityDataMapper() {
    }

    public Level convertLevelEntityToLevel(LevelEntity levelEntity) {
        Level level = new Level();
        level.setCode(levelEntity.getCode());
        level.setName(levelEntity.getName());
        level.setSublevels(convertSubLevelEntityToSubLevel(levelEntity.getSublevels()));
        return level;
    }

    private List<SubLevel> convertSubLevelEntityToSubLevel(List<SubLevelEntity> subLevelEntityList) {
        List<SubLevel> subLevels = new LinkedList<>();
        if (subLevelEntityList != null) {
            for (SubLevelEntity subLevelEntity : subLevelEntityList) {
                subLevels.add(convertSubLevelEntityToSubLevel(subLevelEntity));
            }
        }
        return subLevels;
    }

    public SubLevel convertSubLevelEntityToSubLevel(SubLevelEntity subLevelEntity) {
        SubLevel subLevel = new SubLevel();
        subLevel.setCode(subLevelEntity.getCode());
        subLevel.setName(subLevelEntity.getName());
        subLevel.setInfoGame(convertInfoGameEntityToInfoGame(subLevelEntity.getInfoGame()));
        subLevel.setInfoTheory(convertInfoTheoryEntityToInfoTheory(subLevelEntity.getInfoTheory()));
        return subLevel;
    }

    private List<InfoTheory> convertInfoTheoryEntityToInfoTheory(List<InfoTheoryEntity> infoTheory) {
        List<InfoTheory> infoTheoryList = new LinkedList<>();
        if (infoTheory != null) {
            for (InfoTheoryEntity infoTheoryEntity : infoTheory) {
                infoTheoryList.add(convertInfoTheoryEntityToInfoTheory(infoTheoryEntity));
            }
        }
        return infoTheoryList;
    }

    private InfoTheory convertInfoTheoryEntityToInfoTheory(InfoTheoryEntity infoTheoryEntity) {
        InfoTheory infoTheory = new InfoTheory();
        infoTheory.setCode(infoTheoryEntity.getCode());
        infoTheory.setInfoTheory(convertViewTypeEntityToViewType(infoTheoryEntity.getInfoTheory()));
        return infoTheory;
    }

    private List<ViewType> convertViewTypeEntityToViewType(List<ViewTypeEntity> viewTypeEntityList) {
        List<ViewType> viewTypeList = new LinkedList<>();
        if (viewTypeEntityList != null) {
            for (ViewTypeEntity viewTypeEntity : viewTypeEntityList) {
                viewTypeList.add(convertViewTypeEntityToViewType(viewTypeEntity));
            }
        }
        return viewTypeList;
    }

    private ViewType convertViewTypeEntityToViewType(ViewTypeEntity viewTypeEntity) {
        ViewType viewType = new ViewType();
        viewType.setData(viewTypeEntity.getData());
        viewType.setType(viewTypeEntity.getType());
        return viewType;
    }

    private List<InfoGame> convertInfoGameEntityToInfoGame(List<InfoGameEntity> infoGameEntityList) {
        List<InfoGame> infoGames = new LinkedList<>();
        if (infoGameEntityList != null) {
            for (InfoGameEntity infoGameEntity : infoGameEntityList) {
                infoGames.add(convertInfoGameEntityToInfoGame(infoGameEntity));
            }
        }
        return infoGames;
    }

    private InfoGame convertInfoGameEntityToInfoGame(InfoGameEntity infoGameEntity) {
        InfoGame infoGame = new InfoGame();
        infoGame.setCode(infoGameEntity.getCode());
        infoGame.setType(infoGameEntity.getType());
        infoGame.setContent(convertGameEntityToGame(infoGameEntity.getContent()));
        return infoGame;
    }

    private List<GameContent> convertGameEntityToGame(List<GameContentEntity> content) {
        List<GameContent> gameContent = new LinkedList<>();
        if (content != null) {
            for (GameContentEntity gameContentEntity : content) {
                gameContent.add(convertGameEntityToGame(gameContentEntity));
            }
        }
        return gameContent;
    }

    private GameContent convertGameEntityToGame(GameContentEntity gameContentEntity) {
        GameContent gameContent = new GameContent();
        gameContent.setCorrect(gameContentEntity.getCorrect());
        gameContent.setData(gameContentEntity.getData());
        gameContent.setType(gameContentEntity.getType());
        return gameContent;
    }

    public List<Level> convert(List<LevelEntity> levelEntityList) {
        List<Level> levels = new LinkedList<>();
        if (levelEntityList != null) {
            for (LevelEntity levelEntity : levelEntityList) {
                levels.add(convertLevelEntityToLevel(levelEntity));
            }
        }
        return levels;
    }
}
