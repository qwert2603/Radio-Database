package data_base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrafikRabotiDataBase extends BaseDataBase {

    private String q = "SELECT g.kod_grafika, s.fio, z.naimenovanie, g.vremya1, z.naimenovanie, g.vremya2, z.naimenovanie, g.vremya3 "
            + " FROM \"DZH_grafik_raboty\" g, \"DZH_sotrudniki\" s, \"DZH_zapisi\" z"
            + " WHERE (g.kod_sotrudnika = s.kod_sotrudnika)"
            + " AND ((g.kod_zapisi1 = z.kod_zapisi)"
            + " OR (g.kod_zapisi2 = z.kod_zapisi)"
            + " OR (g.kod_zapisi3 = z.kod_zapisi))";

    private PreparedStatement distinctZapisi;
    private PreparedStatement distinctSotrudniki;

    public GrafikRabotiDataBase() throws SQLException {
        distinctZapisi = getConnection().prepareStatement("SELECT DISTINCT kod_zapisi, naimenovanie FROM \"DZH_zapisi\"");
        distinctSotrudniki = getConnection().prepareStatement("SELECT DISTINCT kod_sotrudnika, fio FROM \"DZH_sotrudniki\"");
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
        return getConnection().prepareStatement("DELETE FROM \"DZH_grafik_raboty\" WHERE (kod_grafika = ?)");
    }

    @Override
    protected PreparedStatement createInsertStatement() throws SQLException {
        return getConnection().prepareStatement("INSERT INTO \"DZH_grafik_raboty\"" +
                " (kod_grafika, kod_sotrudnika, kod_zapisi1, vremya1, kod_zapisi2, vremya2, kod_zapisi3, vremya3)" +
                " VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)");
    }

    public ResultSet queryDistinctZapisi() throws SQLException {
        return distinctZapisi.executeQuery();
    }

    public ResultSet queryDistinctSotrudniki() throws SQLException {
        return distinctSotrudniki.executeQuery();
    }
}
