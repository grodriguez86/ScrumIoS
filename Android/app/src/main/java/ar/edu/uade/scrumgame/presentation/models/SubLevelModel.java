package ar.edu.uade.scrumgame.presentation.models;

import java.util.List;

public class SubLevelModel {
    private String name;
    private String code;
    private List<InfoTheoryModel> infoTheory;
    private List<InfoGameModel> infoGame;


    public SubLevelModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<InfoTheoryModel> getInfoTheory() {
        return infoTheory;
    }

    public void setInfoTheory(List<InfoTheoryModel> infoTheory) {
        this.infoTheory = infoTheory;
    }

    public List<InfoGameModel> getInfoGame() {
        return infoGame;
    }

    public void setInfoGame(List<InfoGameModel> infoGame) {
        this.infoGame = infoGame;
    }

    public Integer calculatePercentage(ProgressModel progressModel, boolean forPrevious) {
        int id = forPrevious ?
                Integer.parseInt(this.code) - 1 :
                Integer.parseInt(this.code);
        if (progressModel.getSublevelID() < id)
            return 0;
        else if (progressModel.getSublevelID() > id)
            return 100;
        else {
            int total = progressModel.getTotalGames() + 1;
            int doneSoFar = progressModel.isTutorialCompleted() ?
                    progressModel.getActualGame() + 1 :
                    progressModel.getActualGame();
            return doneSoFar * 100 / total;
        }
    }
}
