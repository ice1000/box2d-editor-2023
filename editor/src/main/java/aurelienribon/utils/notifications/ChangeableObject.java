package aurelienribon.utils.notifications;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aurelien Ribon | <a href="http://www.aurelienribon.com">...</a>
 */
public class ChangeableObject implements Changeable {
    private final List<ChangeListener> changeListeners = new ArrayList<>(3);

    @Override
    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }

    protected void firePropertyChanged(String propertyName) {
        for (ChangeListener listener : changeListeners)
            listener.propertyChanged(this, propertyName);
    }
}
