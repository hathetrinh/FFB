package com.phieubengoan.game.crazybird.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool;

import com.phieubengoan.game.crazybird.loader.FBirdAssetManager;

public class PlayerComponent implements Component, Pool.Poolable {

    public enum PlayerState {FLY, DIE}

    public OrthographicCamera cam;
    public boolean isDead = false;
    public boolean isGround = false;
    public int point = 0;
    public PlayerState state = PlayerState.FLY;

    public void die() {
        this.isDead = true;
        state = PlayerState.DIE;
        FBirdAssetManager.getInstance().playMusic(FBirdAssetManager.Audios.hit);
    }

    @Override
    public void reset() {
        isDead = false;
        point = 0;
        cam = null;
        isGround = false;
        state = PlayerState.FLY;
    }
}
