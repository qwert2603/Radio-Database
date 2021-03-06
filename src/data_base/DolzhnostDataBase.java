package data_base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DolzhnostDataBase extends BaseDataBase {

    private String q = "SELECT kod_dolzhnosti, naimenovanie, oklad, trebovaniya, obyazannosti"
            + " FROM \"DZH_dolzhnosti\"";

    private final PreparedStatement byOklad;
    private final PreparedStatement byNameAndOklad;

    public DolzhnostDataBase() throws SQLException {
        byOklad = getConnection().prepareStatement(q + " WHERE (oklad >= ?)");
        byNameAndOklad = getConnection().prepareStatement(q + " WHERE (\"naimenovanie\" LIKE CONCAT('%', ?, '%')) AND (oklad >= ?)");


        //getConnection().prepareStatement("DELETE FROM \"DZH_dolzhnosti\"").execute();

        /*PreparedStatement preparedStatement = getConnection().prepareStatement(
                "INSERT INTO \"DZH_dolzhnosti\" VALUES(?, ?, ?, ?, DEFAULT)"
        );
        for (int i = 0; i < 2305; i++) {
            preparedStatement.setInt(1, 8 + r.nextInt(42));
            preparedStatement.setString(2, gR(true));
            preparedStatement.setString(3, gR(true));
            preparedStatement.setString(4, gR(false));
            preparedStatement.execute();
        }*/
    }

    @Override
    protected PreparedStatement createByIdStatement() throws SQLException {
        return getConnection().prepareStatement(q + " WHERE (kod_dolzhnosti = ?)");
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
        return getConnection().prepareStatement("DELETE FROM \"DZH_dolzhnosti\" WHERE (kod_dolzhnosti = ?)");
    }

    @Override
    protected PreparedStatement createInsertStatement() throws SQLException {
        return getConnection().prepareStatement("INSERT INTO \"DZH_dolzhnosti\"" +
                " (kod_dolzhnosti, naimenovanie, oklad, trebovaniya, obyazannosti) VALUES(DEFAULT, ?, ?, ?, ?)");
    }

    @Override
    protected PreparedStatement createUpdateStatement() throws SQLException {
        return getConnection().prepareStatement("UPDATE \"DZH_dolzhnosti\"" +
                " SET naimenovanie = ?, oklad = ?, trebovaniya = ?, obyazannosti = ?" +
                " WHERE (kod_dolzhnosti = ?)");
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
