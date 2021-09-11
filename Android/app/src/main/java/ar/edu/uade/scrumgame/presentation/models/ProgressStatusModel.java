package ar.edu.uade.scrumgame.presentation.models;

import android.content.Context;

import java.util.HashMap;

import ar.edu.uade.scrumgame.R;

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

    public String getStatusText(Context context) {
        String[] progressData = context.getResources().getStringArray(R.array.progress_status);
        HashMap<String, String> progressMap = new HashMap<>();
        for(int i=0; i<progressData.length; i+=2) {
            progressMap.put(progressData[i], progressData[i+1]);
        }
        return progressMap.get(this.status);
    }
}
