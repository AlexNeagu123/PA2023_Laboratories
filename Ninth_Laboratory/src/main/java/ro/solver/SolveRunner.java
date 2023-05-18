package ro.solver;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ro.entity.Album;
import ro.exceptions.FindException;
import ro.factory.JpaRepositoryFactory;
import ro.factory.RepositoryFactory;
import ro.repository.jpa.manager.EntityManagerSingleton;

@Log4j2
public class SolveRunner {
    public static void main(String[] args) {
        try {
            EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManagerFactory().createEntityManager();
            RepositoryFactory repositoryFactory = new JpaRepositoryFactory(entityManager);
            ConstraintSolver constraintSolver = new ConstraintSolver(repositoryFactory.createAlbumRepository(), 2, 10);
            System.out.println("The optimal set of albums is: ");
            for (Album album : constraintSolver.generateOptimalSet()) {
                System.out.println(album);
            }
        } catch (FindException ex) {
            log.error(ex.getMessage());
        }
    }
}
