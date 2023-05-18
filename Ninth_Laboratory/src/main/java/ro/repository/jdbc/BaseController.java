package ro.repository.jdbc;

import java.sql.Connection;

public abstract class BaseController {
    protected Connection con;

    public BaseController(Connection con) {
        this.con = con;
    }
}
