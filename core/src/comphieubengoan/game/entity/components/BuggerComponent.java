package comphieubengoan.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class BuggerComponent implements Component, Pool.Poolable {

    public boolean isAlive = true;

    @Override
    public void reset() {
        isAlive = true;
    }
}
