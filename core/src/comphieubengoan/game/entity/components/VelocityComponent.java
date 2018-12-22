package comphieubengoan.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VelocityComponent implements Component, Pool.Poolable {
    private float vx = 0.0f;
    private float vy = 0.0f;

    @Override
    public void reset() {
        vx = 0.0f;
        vy = 0.0f;
    }
}
