package aurelienribon.bodyeditor;

import aurelienribon.bodyeditor.models.RigidBodyModel;
import aurelienribon.utils.notifications.ChangeableObject;
import aurelienribon.utils.notifications.ObservableList;

import java.util.List;

/**
 * @author Aurelien Ribon | <a href="http://www.aurelienribon.com/">...</a>
 */
public class RigidBodiesManager extends ChangeableObject {
    public static final String PROP_SELECTION = "selection";

    private final ObservableList<RigidBodyModel> models = new ObservableList<>(this);
    private RigidBodyModel selectedModel;

    public RigidBodiesManager() {
        models.addListChangedListener((source, added, removed) -> {
            if (!models.contains(selectedModel)) select(null);
        });
    }

    public ObservableList<RigidBodyModel> getModels() {
        return models;
    }

    public RigidBodyModel getSelectedModel() {
        assert selectedModel == null || models.contains(selectedModel);
        return selectedModel;
    }

    public void select(RigidBodyModel model) {
        assert model == null || models.contains(model);
        selectedModel = model;
        firePropertyChanged(PROP_SELECTION);
    }

    public RigidBodyModel getModel(String name) {
        for (RigidBodyModel model : models)
            if (model.getName().equals(name)) return model;
        return null;
    }
}
