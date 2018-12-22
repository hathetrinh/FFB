package comphieubengoan.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerComponent implements Component, Pool.Poolable {

    private OrthographicCamera cam;
    private boolean isDead;
    private int point;

    @Override
    public void reset() {
        isDead = false;
        point = 0;
        cam = null;
    }
}
