package ar.edu.uade.scrumgame.presentation.models;

public class UserOverallDataModel {
    private String pK = "currentAvailableLevel";

    private int currentAvailableLevel = 1;

    public String getpK() {
        return pK;
    }

    public void setpK(String pK) {
        this.pK = pK;
    }

    public int getCurrentAvailableLevel() {
        return currentAvailableLevel;
    }

    public void setCurrentAvailableLevel(int currentAvailableLevel) {
        this.currentAvailableLevel = currentAvailableLevel;
    }

    public UserOverallDataModel(int currentAvailableLevel) {
        this.currentAvailableLevel = currentAvailableLevel;
    }

    public UserOverallDataModel() { }
}
