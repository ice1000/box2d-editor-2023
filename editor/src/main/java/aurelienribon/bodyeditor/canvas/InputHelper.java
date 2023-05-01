package aurelienribon.bodyeditor.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

/**
 * @author Aurelien Ribon | <a href="http://www.aurelienribon.com/">...</a>
 */
public class InputHelper {
    public static boolean isCtrlDown() {
        return Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)
                || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)
                || Gdx.input.isKeyPressed(Keys.C);
    }
}
