package ar.edu.uade.scrumgame.domain;

import java.util.List;

public class InfoGame {
    private String code;
    private String type;
    private String title;
    private List<GameContent> content;

    public InfoGame() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GameContent> getContent() {
        return content;
    }

    public void setContent(List<GameContent> content) {
        this.content = content;
    }
}
