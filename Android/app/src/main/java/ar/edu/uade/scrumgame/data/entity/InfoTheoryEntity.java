package ar.edu.uade.scrumgame.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoTheoryEntity {
    private String code;
    @SerializedName("content")
    private List<ViewTypeEntity> infoTheory;

    public InfoTheoryEntity() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ViewTypeEntity> getInfoTheory() {
        return infoTheory;
    }

    public void setInfoTheory(List<ViewTypeEntity> infoTheory) {
        this.infoTheory = infoTheory;
    }
}
