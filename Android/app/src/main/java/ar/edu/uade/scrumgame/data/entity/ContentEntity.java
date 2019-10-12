package ar.edu.uade.scrumgame.data.entity;

import java.util.List;

public class ContentEntity {
    private List<LevelEntity> levels;

    public ContentEntity() {
    }

    public List<LevelEntity> getLevels() {
        return levels;
    }

   public void setLevels(List<LevelEntity> levels) {
        this.levels = levels;
    }
}
