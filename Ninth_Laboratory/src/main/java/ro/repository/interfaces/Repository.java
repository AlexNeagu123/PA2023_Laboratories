package ro.repository.interfaces;

import ro.exceptions.CreateException;
import ro.exceptions.DeleteException;
import ro.exceptions.FindException;
import ro.exceptions.UpdateException;

import java.util.List;

/**
 * Interface for generic CRUD operations provided by all the <tt>Repositories</tt>.
 *
 * @param <T> The object model that corresponds to a database entity
 */
public interface Repository<T> {

    /**
     * Inserts a new row in the corresponding table.
     * <p>
     * The newly inserted object doesn't need to have any id value yet.
     * The id is created at the database level and the object gets updated.
     *
     * @param entity The entity whose data should be inserted into the database
     * @return The {@code entity} that also stores the id from the database
     * @throws CreateException If there is an SQL related exception in the insert process
     */
    T create(T entity) throws CreateException;

    /**
     * Finds a row in the database corresponding to a given id and stores it as an entity object.
     *
     * @param id The id of the database row
     * @return The entity found in the database
     * @throws FindException If there is an SQL related exception in the find process
     */
    T findById(int id) throws FindException;

    T findByName(String name) throws FindException;

    List<T> findAll() throws FindException;

    /**
     * Performs a database update based on a given entity object
     *
     * @param entity The model that should be updated. The id field is mandatory.
     * @throws UpdateException If there is an SQL related exception in the update process
     */
    void update(T entity) throws UpdateException;

    /**
     * Performs a database deletion based on a given row id
     *
     * @param id The id of the database entity that should be deleted
     * @throws DeleteException If there is an SQL related exception in the deletion process
     */
    void deleteById(int id) throws DeleteException;
}
