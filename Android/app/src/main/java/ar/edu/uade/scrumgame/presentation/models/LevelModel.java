package ar.edu.uade.scrumgame.presentation.models;

import java.util.List;

public class LevelModel {
    private String name;
    private Integer code;
    private List<SubLevelModel> sublevels;

    public LevelModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<SubLevelModel> getSublevels() {
        return sublevels;
    }

    public void setSublevels(List<SubLevelModel> sublevels) {
        this.sublevels = sublevels;
    }

    public int calculateProgressPercentage(ProgressModel progressModel) {
        if (sublevels == null || sublevels.size() == 0)
            return 0;
        int total = sublevels.size() * 100;
        int subLevelsCompleted = calculateSubslevelsCompleted(progressModel);
        int currentSublevelPercentage = calculateCurrentSublevelPercentage(progressModel);
        int doneSofar = subLevelsCompleted * 100 * currentSublevelPercentage;
        return doneSofar * 100 / total;
    }

    private int calculateSubslevelsCompleted(ProgressModel progressModel) {
        return progressModel.getSublevelID() <= 1 ?
                0 :
                progressModel.getSublevelID() - 1;
    }

    private int calculateCurrentSublevelPercentage(ProgressModel progressModel) {
        return (progressModel.getSublevelID() == 0 ||
                this.sublevels.size() < progressModel.getSublevelID()) ?
                0 :
                this.sublevels.get(progressModel.getSublevelID() - 1)
                        .calculatePercentage(progressModel, false);

    }
}
