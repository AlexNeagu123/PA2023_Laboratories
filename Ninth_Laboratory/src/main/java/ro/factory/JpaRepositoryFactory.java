package ro.factory;

import jakarta.persistence.EntityManager;
import ro.repository.interfaces.AlbumRepository;
import ro.repository.interfaces.ArtistRepository;
import ro.repository.interfaces.GenreRepository;
import ro.repository.interfaces.PlaylistRepository;
import ro.repository.jpa.AlbumRepositoryImpl;
import ro.repository.jpa.ArtistRepositoryImpl;
import ro.repository.jpa.GenreRepositoryImpl;
import ro.repository.jpa.PlaylistRepositoryImpl;

/**
 * This class implements the {@link RepositoryFactory} interfaces and provides
 * JPA implementations for the {@link AlbumRepository}, {@link ArtistRepository}, {@link GenreRepository} and {@link PlaylistRepository}
 * interfaces.
 *
 * @author Alex Neagu
 */
public class JpaRepositoryFactory implements RepositoryFactory {
    public EntityManager entityManager;

    public JpaRepositoryFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AlbumRepository createAlbumRepository() {
        return new AlbumRepositoryImpl(entityManager);
    }

    @Override
    public ArtistRepository createArtistRepository() {
        return new ArtistRepositoryImpl(entityManager);
    }

    @Override
    public GenreRepository createGenreRepository() {
        return new GenreRepositoryImpl(entityManager);
    }

    @Override
    public PlaylistRepository createPlaylistRepository() {
        return new PlaylistRepositoryImpl(entityManager);
    }
}
