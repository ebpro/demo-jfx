package fr.univtln.bruno.demos.demojfx;

import fr.univtln.bruno.demos.demojfx.model.DataAccessException;
import fr.univtln.bruno.demos.demojfx.model.Model;
import fr.univtln.bruno.demos.demojfx.model.Person;
import fr.univtln.bruno.demos.demojfx.model.PersonDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.logging.Logger;

public class PersonController {
    private static final Logger log = Logger.getLogger(PersonController.class.getName());

    private final PersonDAO personDAO;

    @FXML
    private TableView<Person> personList;

    public PersonController() throws DataAccessException {
        personDAO = Model.get().getPersonDAO();
    }

    public void updatePersonList() {
        try {
            personList.getItems().clear();
            personList.getItems().addAll(personDAO.findAll().getContent());
        } catch (DataAccessException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    public void removePerson() {
        try {
            log.info("Deleting " + personList.getSelectionModel().getSelectedItem());
            personDAO.delete(personList.getSelectionModel().getSelectedItem());
        } catch (DataAccessException e) {
            log.warning(e.getLocalizedMessage());
        }
    }
}