package aurelienribon.bodyeditor.models;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Aurelien Ribon | <a href="http://www.aurelienribon.com/">...</a>
 */
public class CircleModel {
    public final Vector2 center = new Vector2();
    public float radius;

    public CircleModel() {
    }

    public CircleModel(Vector2 center, float radius) {
        this.center.set(center);
        this.radius = radius;
    }
}
