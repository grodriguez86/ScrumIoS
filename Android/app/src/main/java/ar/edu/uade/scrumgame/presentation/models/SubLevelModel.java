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
}
