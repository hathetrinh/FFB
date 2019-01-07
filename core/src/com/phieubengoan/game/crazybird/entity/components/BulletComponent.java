package com.phieubengoan.game.crazybird.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class BulletComponent implements Component, Pool.Poolable {

    public enum Owner {PLAYER, ENEMY, NONE}
    public enum BulletState {SHUT, HIDE}

    public boolean isDead = false;
    public Owner owner = Owner.NONE;
    public Vector2 velocity = Vector2.Zero;
    public BulletState state = BulletState.HIDE;

    @Override
    public void reset() {
        isDead = true;
        owner = Owner.NONE;
        state = BulletState.HIDE;
    }
}
