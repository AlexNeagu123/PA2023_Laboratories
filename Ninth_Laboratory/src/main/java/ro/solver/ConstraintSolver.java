package ro.solver;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;
import ro.entity.Album;
import ro.exceptions.FindException;
import ro.repository.interfaces.AlbumRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods for finding a set of at least
 * k albums having titles that start with the same letter,
 * and they are released in the same period of time.
 * For any two albums in the result, the difference between their release years must not exceed a given value p.
 * <p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Constraint_programming">Constraint Based Programming</a>
 */
public class ConstraintSolver {
    private final AlbumRepository albumRepository;
    private final Model model;
    private final int maxDelta;
    private final int minCardinality;

    private List<Album> albumList;

    public ConstraintSolver(AlbumRepository albumRepository, int maxDelta, int minCardinality) {
        this.albumRepository = albumRepository;
        this.model = new Model("Lab9 Problem");
        this.maxDelta = maxDelta;
        this.minCardinality = minCardinality;
        this.albumList = new ArrayList<>();
    }

    private int getRelationshipIndex(Album firstAlbum, Album secondAlbum) {
        if (firstAlbum.getTitle().charAt(0) != secondAlbum.getTitle().charAt(0)) {
            return 1;
        }
        if (Math.abs(firstAlbum.getReleaseYear() - secondAlbum.getReleaseYear()) > maxDelta) {
            return 1;
        }
        return 2;
    }

    private List<Album> retrieveOptimalSet(Solver solver, BoolVar[] vars) {
        List<Album> optimalSet = new ArrayList<>();
        if (solver.solve()) {
            for (int i = 0; i < vars.length; ++i) {
                if (vars[i].getValue() == 1) {
                    optimalSet.add(this.albumList.get(i));
                }
            }
        }
        return optimalSet;
    }

    public List<Album> generateOptimalSet() throws FindException {
        this.albumList = albumRepository.findAll();
        int albumCount = albumList.size();

        BoolVar[] vars = model.boolVarArray("vars", albumCount);
        for (int i = 0; i < albumCount; ++i) {
            for (int j = i + 1; j < albumCount; j++) {
                int constrainedSum = getRelationshipIndex(albumList.get(i), albumList.get(j));
                if (constrainedSum == 2) {
                    continue;
                }
                model.arithm(vars[i], "+", vars[j], "<=", constrainedSum).post();
            }
        }

        model.sum(vars, ">=", minCardinality).post();
        return retrieveOptimalSet(model.getSolver(), vars);
    }
}
