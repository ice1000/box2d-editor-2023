package aurelienribon.bodyeditor;

import aurelienribon.bodyeditor.ui.MainWindow;

/**
 * @author Aurelien Ribon | <a href="http://www.aurelienribon.com/">...</a>
 */
public class Ctx {
    public static final IoManager io = new IoManager();
    public static final RigidBodiesManager bodies = new RigidBodiesManager();
    public static final DynamicObjectsManager objects = new DynamicObjectsManager();
    public static final MainWindow window = new MainWindow();
}
