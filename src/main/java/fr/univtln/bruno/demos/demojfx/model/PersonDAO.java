package fr.univtln.bruno.demos.demojfx.model;

import java.beans.PropertyChangeSupport;
import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PersonDAO implements Closeable {

    private static final Logger log = Logger.getLogger(PersonDAO.class.getName());
    private final Connection connection;
    private final PreparedStatement findAllPS;
    private final PreparedStatement deletePS;

    private final PreparedStatement persistPS;
    private final PropertyChangeSupport modelChangeSupport;

    public PersonDAO(PropertyChangeSupport modelChangeSupport) throws DataAccessException {
        try {
            this.connection = DBManager.JDBC_CONNECTION_POOL.getConnection();
            this.persistPS = connection.prepareStatement("INSERT INTO PERSON(NAME, FIRSTNAME) VALUES (?, ?);",  Statement.RETURN_GENERATED_KEYS);
            this.deletePS = connection.prepareStatement("DELETE FROM PERSON WHERE ID=?");
            this.findAllPS = connection.prepareStatement("SELECT * FROM PERSON LIMIT ? OFFSET ?");
            this.modelChangeSupport = modelChangeSupport;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Person persist(Person person) throws DataAccessException {
        return persist(person.getName(), person.getFirstname());
    }

    public Person persist(String name, String firstname) throws DataAccessException {
        try {
            persistPS.setString(1, name);
            persistPS.setString(1, firstname);
            int result = persistPS.executeUpdate();

            int generatedKey = -1 ;
            if (persistPS.executeUpdate() > 0) {
                // Retrieves any auto-generated keys created as a result of executing this Statement object
                java.sql.ResultSet generatedKeys = persistPS.getGeneratedKeys();
                if ( generatedKeys.next() ) {
                    generatedKey = generatedKeys.getInt(1);
                }
            }


            Person person = Person.of(generatedKey,name, firstname);
            log.info("inserting person"+person);
            log.info(findAll().toString());

            modelChangeSupport.firePropertyChange(Person.class.getName(), null, generatedKey);

            return person;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Page<Person> findAll() throws DataAccessException {
        return findAll(1, DBManager.MAX_PAGE_SIZE);
    }

    public Page<Person> findAll(int pageNumber, int pageSize) throws DataAccessException {
        List<Person> result = new ArrayList<>();
        int realPageSize = Integer.max(pageSize, DBManager.MAX_PAGE_SIZE);
        try (Statement statement = DBManager.JDBC_CONNECTION_POOL.getConnection().createStatement()) {
            findAllPS.setInt(1, realPageSize);
            findAllPS.setInt(2, (pageNumber - 1) * realPageSize);
            ResultSet resultSet = findAllPS.executeQuery();
            while (resultSet.next()) {
                result.add(Person.builder()
                        .id(resultSet.getInt("ID"))
                        .name(resultSet.getString("NAME"))
                        .firstname(resultSet.getString("FIRSTNAME"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return Page.of(result);
    }

    public int delete(Person person) throws DataAccessException {
        return delete(person.getId());
    }

    private int delete(long id) throws DataAccessException {
        try {
            findAllPS.setLong(1, id);
            int result = deletePS.executeUpdate();
            log.info("Deleting person with id:" + id + " (" + result + ")");
            log.info(findAll().toString());

            modelChangeSupport.firePropertyChange(Person.class.getName(), id, null);

            return result;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e.getMessage());
        }
    }
}
