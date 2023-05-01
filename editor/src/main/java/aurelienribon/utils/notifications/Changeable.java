package aurelienribon.utils.notifications;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com
 */
public interface Changeable {
    void addChangeListener(ChangeListener l);

    void removeChangeListener(ChangeListener l);
}
