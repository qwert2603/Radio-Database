package data_base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DolzhnostDataBase extends BaseDataBase {

    private String q = "SELECT naimenovanie, oklad, trebovaniya, obyazannosti"
            + " FROM \"DZH_dolzhnosti\"";

    private final PreparedStatement byOklad;
    private final PreparedStatement byNameAndOklad;

    public DolzhnostDataBase() throws SQLException {
        byOklad = getConnection().prepareStatement(q + " WHERE (oklad > ?)");
        byNameAndOklad = getConnection().prepareStatement(q + " WHERE (\"naimenovanie\" LIKE CONCAT('%', ?, '%')) AND (oklad > ?)");
    }

    @Override
    protected PreparedStatement createByNameStatement() throws SQLException {
        return getConnection().prepareStatement(q + " WHERE (\"naimenovanie\" LIKE CONCAT('%', ?, '%'))");
    }

    @Override
    protected PreparedStatement createSelectAllStatement() throws SQLException {
        return getConnection().prepareStatement(q);
    }

    public ResultSet queryByOklad(int oklad) throws SQLException {
        if (oklad == 0) {
            return queryAll();
        }
        byOklad.setInt(1, oklad);
        return byOklad.executeQuery();
    }

    public ResultSet queryByNameOklad(String name, int oklad) throws SQLException {
        if (name.isEmpty()) {
            return queryByOklad(oklad);
        }
        if (oklad == 0) {
            return queryByName(name);
        }
        byNameAndOklad.setString(1, name);
        byNameAndOklad.setInt(2, oklad);
        return byNameAndOklad.executeQuery();
    }

}
