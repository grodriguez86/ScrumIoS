package ar.edu.uade.scrumgame.presentation.models;

import java.util.List;

import ar.edu.uade.scrumgame.domain.Progress;
import ar.edu.uade.scrumgame.presentation.constants.ProgressStatusConstant;
import io.realm.internal.EmptyLoadChangeSet;

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
                getIntCode() - 1 :
                getIntCode();
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

    public int getIntCode() {
        return Integer.parseInt(code.split("\\.")[1]);
    }

    public ProgressStatusModel getStatus(ProgressModel progressModel) {
        int current = progressModel.getSublevelID();
        int progressPercentage = calculatePercentage(progressModel, false);
        int id = getIntCode();
        if (current == 0) {
            if (id == 1)
                return new ProgressStatusModel(ProgressStatusConstant.AVAILABLE, progressPercentage);
            else
                return new ProgressStatusModel(ProgressStatusConstant.LOCKED, progressPercentage);
        } else {
            if (current < id) {
                if (current == id - 1) {
                    int lastSublevelProgress = calculatePercentage(progressModel, true);
                    return (lastSublevelProgress == 100) ?
                            new ProgressStatusModel(ProgressStatusConstant.AVAILABLE, progressPercentage) :
                            new ProgressStatusModel(ProgressStatusConstant.LOCKED, progressPercentage);
                } else
                    return new ProgressStatusModel(ProgressStatusConstant.LOCKED, progressPercentage);
            } else {
                switch (progressPercentage) {
                    case 0:
                        return new ProgressStatusModel(ProgressStatusConstant.AVAILABLE, progressPercentage);
                    case 100:
                        return new ProgressStatusModel(ProgressStatusConstant.FINISHED, progressPercentage);
                    default:
                        return new ProgressStatusModel(ProgressStatusConstant.STARTED, progressPercentage);
                }
            }
        }
    }
}
