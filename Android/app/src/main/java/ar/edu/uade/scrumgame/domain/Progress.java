package ar.edu.uade.scrumgame.domain;


public class Progress {
    private String pK;
    /**
     * Id del nivel. Si no hay Progress para el nivel, está bloqueado
     */
    private int levelId;
    /**
     * Subnivel alcanzado (0 significa sin iniciar)
     */
    private int sublevelID;
    /**
     * Si fue completado o salteado
     */
    private boolean tutorialCompleted;
    /**
     * Juego actual (0 significa que no empezó o que no pasó el primero dependiendo del resto
     */
    private int actualGame;

    private int totalGames;

    private String status;

    private boolean blocked;

    public int getLevelId() {
        return levelId;
    }

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

    public String getpK() {
        return pK;
    }

    public void setpK(String pK) {
        this.pK = pK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}