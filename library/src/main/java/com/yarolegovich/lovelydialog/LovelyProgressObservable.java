package com.yarolegovich.lovelydialog;

import java.util.Observable;

public class LovelyProgressObservable extends Observable {
    private int progress = 0;

    public void setProgress(int progress) {
        this.progress = progress;
        setChanged();
        notifyObservers(progress);
    }

    public int getProgress() {
        return progress;
    }
}
