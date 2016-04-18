package data_base;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ZhanrDataBase extends BaseDataBase {

    private String q = "SELECT kod_zhanra, naimenovanie, opisanie "
            + " FROM \"DZH_zhanri\"";

    public ZhanrDataBase() throws SQLException {
        /*PreparedStatement preparedStatement = getConnection().prepareStatement(
                "INSERT INTO \"DZH_zhanri\" VALUES(DEFAULT, ?, ?)"
        );
        for (int i = 0; i < 2305; i++) {
            preparedStatement.setString(1, gR(false));
            preparedStatement.setString(2, gR(true));
            preparedStatement.execute();
        }*/
    }

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
        return getConnection().prepareStatement("DELETE FROM \"DZH_zhanri\" WHERE (kod_zhanra = ?)");
    }

    @Override
    protected PreparedStatement createInsertStatement() throws SQLException {
        return getConnection().prepareStatement("INSERT INTO \"DZH_zhanri\" (kod_zhanra, naimenovanie, opisanie) VALUES(DEFAULT, ?, ?)");
    }
}
