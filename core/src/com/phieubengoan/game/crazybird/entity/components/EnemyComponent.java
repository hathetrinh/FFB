package com.phieubengoan.game.crazybird.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {

    public enum EnemyState {RUN, SHUT, DIE, HIDE}

    public EnemyState state = EnemyState.HIDE;
    public float shootDelay = 5f;
    public boolean isShutter = false;

    @Override
    public void reset() {
        state = EnemyState.HIDE;
        shootDelay = 10f;
        isShutter = false;
    }
}
