package data_base;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ZhanrDataBase extends BaseDataBase {

    private String q = "SELECT naimenovanie, opisanie "
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

}
