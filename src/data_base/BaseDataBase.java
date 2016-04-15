package data_base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDataBase {

    private final Connection c;
    private PreparedStatement byNameStatement;
    private PreparedStatement allStatement;

    public BaseDataBase() throws SQLException {
        //c = DriverManager.getConnection("jdbc:postgresql://data.biysk.secna.ru:5432/test", "test", "Aigee9");
        c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "test", "qwert");
    }

    protected final Connection getConnection() {
        return c;
    }

    protected abstract PreparedStatement createByNameStatement() throws SQLException;

    protected abstract PreparedStatement createSelectAllStatement() throws SQLException;

    public ResultSet queryByName(String q) throws SQLException {
        if (q.isEmpty()) {
            return queryAll();
        }
        if (byNameStatement == null) {
            byNameStatement = createByNameStatement();
        }
        byNameStatement.setString(1, q);
        return byNameStatement.executeQuery();
    }

    public ResultSet queryAll() throws SQLException {
        if (allStatement == null) {
            allStatement = createSelectAllStatement();
        }
        return allStatement.executeQuery();
    }

}
