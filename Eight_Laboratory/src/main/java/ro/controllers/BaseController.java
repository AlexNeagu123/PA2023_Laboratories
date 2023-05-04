package ro.controllers;

import java.sql.Connection;

public abstract class BaseController {
    protected Connection con;

    public BaseController(Connection con) {
        this.con = con;
    }
}
