package fr.univtln.bruno.demos.demojfx.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model {
    private static Model modelSingleton;
    private final PropertyChangeSupport modelChangeSupport = new PropertyChangeSupport(this);

    private Model() {
    }

    public static Model get() {
        if (modelSingleton == null) modelSingleton = new Model();
        return modelSingleton;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.modelChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.modelChangeSupport.removePropertyChangeListener(listener);
    }

    public PersonDAO getPersonDAO() throws DataAccessException {
        return new PersonDAO(modelChangeSupport);
    }

}
