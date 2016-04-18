package data_base;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IspolnitelDataBase extends BaseDataBase {

    private String q = "SELECT kod_ispolnitelya, naimenovanie, opisanie " +
            " FROM \"DZH_ispolniteli\"";

    public IspolnitelDataBase() throws SQLException {}

    @Override
    protected PreparedStatement createByNameStatement() throws SQLException {
        return getConnection().prepareStatement(q + " WHERE (\"naimenovanie\" LIKE CONCAT('%', ?, '%'))");
    }

    @Override
    protected PreparedStatement createSelectAllStatement() throws SQLException {
       return getConnection().prepareStatement(q);
    }

    @Override
    protected PreparedStatement createDeleteStatement() throws SQLException {
        return getConnection().prepareStatement("DELETE FROM \"DZH_ispolniteli\" WHERE (kod_ispolnitelya = ?)");
    }

    @Override
    protected PreparedStatement createInsertStatement() throws SQLException {
        return getConnection().prepareStatement("INSERT INTO \"DZH_ispolniteli\" VALUES(DEFAULT, ?, ?)");
    }

}
