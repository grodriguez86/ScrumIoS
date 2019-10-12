package ar.edu.uade.scrumgame.domain;

import java.util.List;

public class InfoTheory {
    private String code;
    private List<ViewType> infoTheory;

    public InfoTheory() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ViewType> getInfoTheory() {
        return infoTheory;
    }

    public void setInfoTheory(List<ViewType> infoTheory) {
        this.infoTheory = infoTheory;
    }
}
