package br.edu.unoesc.db;

public class DbException extends RuntimeException {
    public DbException(String msg) {
        super("DAO: " + msg);
    }
}
