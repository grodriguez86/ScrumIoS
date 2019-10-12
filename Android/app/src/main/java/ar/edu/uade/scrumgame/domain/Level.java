package ar.edu.uade.scrumgame.domain;

import java.util.List;

public class Level {
    private String name;
    private Integer code;
    private List<SubLevel> sublevels;

    public Level() {
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

    public List<SubLevel> getSublevels() {
        return sublevels;
    }

    public void setSublevels(List<SubLevel> sublevels) {
        this.sublevels = sublevels;
    }
}
