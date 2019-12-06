package ar.edu.uade.scrumgame.presentation.mapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.domain.GameContent;
import ar.edu.uade.scrumgame.domain.InfoGame;
import ar.edu.uade.scrumgame.presentation.di.PerActivity;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.models.InfoGameModel;

@PerActivity
public class InfoGameModelDataMapper {

    @Inject
    InfoGameModelDataMapper() {
    }

    public List<InfoGameModel> transform(Collection<InfoGame> infoGameList) {
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
        infoGameModel.setTitle(infoGame.getTitle());
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
}
