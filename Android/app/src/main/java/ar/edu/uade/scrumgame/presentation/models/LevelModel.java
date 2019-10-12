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
}
