package ro.repository.jpa.manager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * A singleton class responsible with
 * the management of an _EntityManagerFactory_ object
 *
 * @author Alex Neagu
 */
public class EntityManagerSingleton {
    private static EntityManagerSingleton instance;
    private final EntityManagerFactory entityManagerFactory;
    private final static String PERSISTENCE_UNIT = "java_lab9";

    private EntityManagerSingleton() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManagerSingleton getInstance() {
        if (instance == null) {
            instance = new EntityManagerSingleton();
        }
        return instance;
    }
}
