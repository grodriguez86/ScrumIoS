package ar.edu.uade.scrumgame.presentation.models;

import java.util.List;

public class InfoGameModel {
    private String code;
    private String type;
    private List<GameContentModel> content;

    public InfoGameModel() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GameContentModel> getContent() {
        return content;
    }

    public void setContent(List<GameContentModel> content) {
        this.content = content;
    }
}
