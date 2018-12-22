package comphieubengoan.game.entity.components;

        import com.badlogic.ashley.core.Component;
        import com.badlogic.gdx.physics.box2d.Body;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhysicComponent implements Component {
    private Body body;
}
