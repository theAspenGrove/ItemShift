package net.mov51.ItemShift.util;

public class Level {
    int level;
    float progress;

    public Level(int level, float progress){
        this.level = level;
        this.progress = progress;
    }

    public int getLevel() {
        return level;
    }

    public float getProgress() {
        return progress;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
