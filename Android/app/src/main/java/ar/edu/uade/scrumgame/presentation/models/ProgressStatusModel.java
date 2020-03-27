package ar.edu.uade.scrumgame.presentation.models;

public class ProgressStatusModel {
    private int progress;

    private String status;

    public ProgressStatusModel(String status,int progress) {
        this.progress = progress;
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
