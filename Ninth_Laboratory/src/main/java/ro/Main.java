package ro;

import lombok.extern.log4j.Log4j2;
import ro.utils.InsertionUtils;

@Log4j2
public class Main {
    public static void main(String[] args) {
        InsertionUtils.insertArtistsWithJPA(1000);
        InsertionUtils.insertArtistsWithJDBC(1000);
        InsertionUtils.insertGenresWithJPA(5);
        InsertionUtils.insertGenresWithJDBC(5);
        InsertionUtils.insertAlbumsWithJPA(1000);
        InsertionUtils.insertAlbumsWithJDBC(1000);
    }
}