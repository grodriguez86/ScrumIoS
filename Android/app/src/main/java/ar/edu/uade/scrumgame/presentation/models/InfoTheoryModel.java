package ar.edu.uade.scrumgame.presentation.models;

import java.util.List;

public class InfoTheoryModel {
    private String code;
    private List<ViewTypeModel> infoTheory;

    public InfoTheoryModel() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ViewTypeModel> getInfoTheory() {
        return infoTheory;
    }

    public void setInfoTheory(List<ViewTypeModel> infoTheory) {
        this.infoTheory = infoTheory;
    }
}
