package ar.edu.uade.scrumgame.data.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class UserOverallDataEntity implements RealmModel {

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
