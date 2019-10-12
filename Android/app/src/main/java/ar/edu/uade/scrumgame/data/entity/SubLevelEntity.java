package ar.edu.uade.scrumgame.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubLevelEntity {
    private String name;
    private String code;
    @SerializedName("info_theory")
    private List<InfoTheoryEntity> infoTheory;
    @SerializedName("info_games")
    private List<InfoGameEntity> infoGame;


    public SubLevelEntity() {
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

    public List<InfoTheoryEntity> getInfoTheory() {
        return infoTheory;
    }

    public void setInfoTheory(List<InfoTheoryEntity> infoTheory) {
        this.infoTheory = infoTheory;
    }

    public List<InfoGameEntity> getInfoGame() {
        return infoGame;
    }

    public void setInfoGame(List<InfoGameEntity> infoGame) {
        this.infoGame = infoGame;
    }
}
