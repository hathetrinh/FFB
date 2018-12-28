package comphieubengoan.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PipeComponent implements Component, Pool.Poolable {

    boolean isDead = false;

    @Override
    public void reset() {
        isDead = false;
    }
}
