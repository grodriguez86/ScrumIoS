package ar.edu.uade.scrumgame.data.entity;

public class GameContentEntity {
    private String type;
    private Boolean isCorrect;
    private String data;

    public GameContentEntity() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
