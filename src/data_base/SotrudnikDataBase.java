package data_base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SotrudnikDataBase extends BaseDataBase {

    private String q = "SELECT s.kod_sotrudnika, s.fio, s.vozrast, s.pol, s.adres, s.telefon, s.pasport, d.naimenovanie"
            + " FROM \"DZH_sotrudniki\" s, \"DZH_dolzhnosti\" d"
            + " WHERE (s.kod_dolzhnosti = d.kod_dolzhnosti) ";

    private PreparedStatement distinctDolzhnosti;

    public SotrudnikDataBase() throws SQLException {
        distinctDolzhnosti = getConnection().prepareStatement("SELECT DISTINCT kod_dolzhnosti, naimenovanie FROM \"DZH_dolzhnosti\"");
    }

    @Override
    protected PreparedStatement createByNameStatement() throws SQLException {
        return getConnection().prepareStatement(q + " AND (s.fio LIKE CONCAT('%', ?, '%'))");
    }

    @Override
    protected PreparedStatement createSelectAllStatement() throws SQLException {
       return getConnection().prepareStatement(q);
    }

    @Override
    protected PreparedStatement createDeleteStatement() throws SQLException {
        return getConnection().prepareStatement("DELETE FROM \"DZH_sotrudniki\" WHERE (kod_sotrudnika = ?)");
    }

    @Override
    protected PreparedStatement createInsertStatement() throws SQLException {
        return getConnection().prepareStatement("INSERT INTO \"DZH_sotrudniki\"" +
                " (kod_sotrudnika, fio, vozrast, pol, adres, telefon, pasport, kod_dolzhnosti)" +
                " VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)");
    }

    public ResultSet queryDistinctDolzhnosti() throws SQLException {
        return distinctDolzhnosti.executeQuery();
    }

}
