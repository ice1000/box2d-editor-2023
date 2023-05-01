package aurelienribon.utils.notifications;

/**
 * @author Aurelien Ribon | <a href="http://www.aurelienribon.com">...</a>
 */
public interface Changeable {
    void addChangeListener(ChangeListener l);

    void removeChangeListener(ChangeListener l);
}
