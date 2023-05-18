package ro.repository.jpa;

import jakarta.persistence.EntityManager;
import ro.repository.interfaces.Repository;

import java.util.List;

/**
 * The generic <tt>AbstractRepository</tt> simplifies the implementations of the <tt>Repository</tt>
 * classes.
 *
 * @author Alex Neagu
 */
public abstract class AbstractRepository<T> implements Repository<T> {
    protected final EntityManager entityManager;

    private final Class<T> classType;

    protected AbstractRepository(EntityManager entityManager, Class<T> classType) {
        this.entityManager = entityManager;
        this.classType = classType;
    }

    @Override
    public T create(T entity) {
        beginTransaction();
        entityManager.persist(entity);
        commitTransaction();
        return entity;
    }

    @Override
    public void update(T entity) {
        beginTransaction();
        entityManager.merge(entity);
        commitTransaction();
    }

    @Override
    public void deleteById(int id) {
        beginTransaction();
        T entity = findById(id);
        entityManager.remove(entity);
        commitTransaction();
    }

    @Override
    public T findById(int id) {
        return entityManager.find(classType, id);
    }

    @Override
    public T findByName(String name) {
        return (T) entityManager.createNamedQuery(classType.getSimpleName() + ".findByName").setParameter("name", name).getSingleResult();
    }

    @Override
    public List<T> findAll() {
        return entityManager.createNamedQuery(classType.getSimpleName() + ".findAll").getResultList();
    }

    protected void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    protected void commitTransaction() {
        entityManager.getTransaction().commit();
    }
}
