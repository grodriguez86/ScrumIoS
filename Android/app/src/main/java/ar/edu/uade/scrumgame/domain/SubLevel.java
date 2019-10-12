package ar.edu.uade.scrumgame.domain;

import java.util.List;

public class SubLevel {
    private String name;
    private String code;
    private List<InfoTheory> infoTheory;
    private List<InfoGame> infoGame;


    public SubLevel() {
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

    public List<InfoTheory> getInfoTheory() {
        return infoTheory;
    }

    public void setInfoTheory(List<InfoTheory> infoTheory) {
        this.infoTheory = infoTheory;
    }

    public List<InfoGame> getInfoGame() {
        return infoGame;
    }

    public void setInfoGame(List<InfoGame> infoGame) {
        this.infoGame = infoGame;
    }
}
