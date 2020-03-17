package ar.edu.uade.scrumgame.data.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserOverallDataEntity extends RealmObject {

    @PrimaryKey
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

    public UserOverallDataEntity(int currentAvailableLevel) {
        this.currentAvailableLevel = currentAvailableLevel;
    }

    public UserOverallDataEntity() { }
}
