DROP TABLE albums CASCADE;
DROP TABLE artists CASCADE;
DROP TABLE genres CASCADE;
DROP TABLE album_genre CASCADE;
DROP TABLE playlists CASCADE;
DROP TABLE playlist_albums_tracking CASCADE;

CREATE TABLE artists (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(100),
    PRIMARY KEY (id)
);

CREATE TABLE albums (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    release_year integer,
    title varchar(100),
    artist_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (artist_id)
        REFERENCES artists (id) ON DELETE CASCADE
);

CREATE TABLE genres (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(100),
    PRIMARY KEY (id)
);

CREATE TABLE album_genre (
    album_id integer NOT NULL,
    genre_id integer NOT NULL,
    PRIMARY KEY (album_id, genre_id),
    FOREIGN KEY (album_id)
        REFERENCES albums (id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id)
        REFERENCES genres (id) ON DELETE CASCADE
);

CREATE TABLE playlists (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    creation_timestamp timestamp,
    name varchar(100),
    PRIMARY KEY (id)
);

CREATE TABLE playlist_albums_tracking (
    playlist_id integer NOT NULL,
    album_id integer NOT NULL,
    PRIMARY KEY (playlist_id, album_id),
    FOREIGN KEY (playlist_id)
        REFERENCES playlists(id) ON DELETE CASCADE,
    FOREIGN KEY (album_id)
        REFERENCES albums(id) ON DELETE CASCADE
)