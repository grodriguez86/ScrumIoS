package ar.edu.uade.scrumgame.data.entity;

import java.util.List;

public class LevelEntity {
    private String name;
    private Integer code;
    private List<SubLevelEntity> sublevels;

    public LevelEntity() {
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

    public List<SubLevelEntity> getSublevels() {
        return sublevels;
    }

    public void setSublevels(List<SubLevelEntity> sublevels) {
        this.sublevels = sublevels;
    }
}
