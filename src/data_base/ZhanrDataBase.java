package data_base;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ZhanrDataBase extends BaseDataBase {

    private String q = "SELECT naimenovanie, opisanie "
            + " FROM \"DZH_zhanri\"";

    public ZhanrDataBase() throws SQLException {
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
