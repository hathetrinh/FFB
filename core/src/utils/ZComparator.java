package utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import comphieubengoan.game.entity.components.Mapper;
import comphieubengoan.game.entity.components.TransformationComponent;

import java.util.Comparator;

public class ZComparator implements Comparator<Entity> {

    private ComponentMapper<TransformationComponent> transform;

    public ZComparator() {
        transform = Mapper.transformationComponents;
    }

    @Override
    public int compare(Entity o1, Entity o2) {

        if (o1 == null && o2 == null) {
            return 0;
        }
        TransformationComponent t1 = transform.get(o1);
        TransformationComponent t2 = transform.get(o2);

        if (t1.position.z > t2.position.z) {
            return 1;
        } else if (t1.position.z < t2.position.z) {
            return -1;
        }
        return 0;
    }
}
