package com.phieubengoan.game.crazybird.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import com.phieubengoan.game.crazybird.loader.FBirdAssetManager;

public class BuggerComponent implements Component, Pool.Poolable {

    public enum BuggerState {SHOW, HIDE}

    public boolean isAlive = true;
    public BuggerState state = BuggerState.HIDE;

    public void die() {
        isAlive = false;
        FBirdAssetManager.getInstance().playMusic(FBirdAssetManager.Audios.point);
    }

    @Override
    public void reset() {
        isAlive = true;
        state = BuggerState.HIDE;
    }
}
