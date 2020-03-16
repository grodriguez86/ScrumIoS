package ar.edu.uade.scrumgame.data.entity;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProgressEntity extends RealmObject {
    /**
     * Id del nivel. Si no hay Progress para el nivel, está bloqueado
     */
    @PrimaryKey
    @SerializedName("level_number")
    private int levelId;
    /**
     * Subnivel alcanzado (0 significa sin iniciar)
     */
    @SerializedName("actual_sublevel")
    private int sublevelID;
    /**
     * Si fue completado o salteado
     */
    @SerializedName("tutorial_completed")
    private boolean tutorialCompleted;
    /**
     * Juego actual (0 significa que no empezó o que no pasó el primero dependiendo del resto
     */
    @SerializedName("actual_game")
    private int actualGame;
    @SerializedName("total_games")
    private int totalGames;

    public ProgressEntity() {
    }

    public ProgressEntity(int levelId, int sublevelID, boolean tutorialCompleted, int actualGame, int totalGames) {
        this.levelId = levelId;
        this.sublevelID = sublevelID;
        this.tutorialCompleted = tutorialCompleted;
        this.actualGame = actualGame;
        this.totalGames = totalGames;
    }

    //@PropertyName("level_id") anotacion para firestore
    public int getLevelId() {
        return levelId;
    }

    //@PropertyName("level_id") anotacion para firestore
    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getSublevelID() {
        return sublevelID;
    }

    public void setSublevelID(int sublevelID) {
        this.sublevelID = sublevelID;
    }

    public boolean isTutorialCompleted() {
        return tutorialCompleted;
    }

    public void setTutorialCompleted(boolean tutorialCompleted) {
        this.tutorialCompleted = tutorialCompleted;
    }

    public int getActualGame() {
        return actualGame;
    }

    public void setActualGame(int actualGame) {
        this.actualGame = actualGame;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }
}