package comphieubengoan.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GameAnimationType implements Component, Pool.Poolable {

    public static final int PLAYER = 0;
    public static final int COIN = 1;

    public int state = 0;
    public float time = 0.0f;
    public boolean loop = false;

    public void set(int state) {
        this.state = state;
        this.time = 0.0f;
    }

    public int get() {
        return state;
    }

    @Override
    public void reset() {

        state = 0;
        time = 0.0f;
        loop = false;

    }
}
