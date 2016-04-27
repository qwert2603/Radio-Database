package data_base;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GrafikRabotiDataBase extends BaseDataBase {

    private String q = "SELECT g.kod_grafika, s.fio, g.data," +
            " z1.naimenovanie, g.vremya1," +
            " z2.naimenovanie, g.vremya2," +
            " z3.naimenovanie, g.vremya3 " +
            " FROM \"DZH_grafik_raboty\" g" +
            " LEFT JOIN \"DZH_sotrudniki\" s ON g.kod_sotrudnika = s.kod_sotrudnika" +
            " LEFT JOIN \"DZH_zapisi\" z1 ON g.kod_zapisi1 = z1.kod_zapisi" +
            " LEFT JOIN \"DZH_zapisi\" z2 ON g.kod_zapisi2 = z2.kod_zapisi" +
            " LEFT JOIN \"DZH_zapisi\" z3 ON g.kod_zapisi3 = z3.kod_zapisi";

    private PreparedStatement distinctZapisi;
    private PreparedStatement distinctSotrudniki;
    private PreparedStatement byData;
    private PreparedStatement byNameAndData;

    public GrafikRabotiDataBase() throws SQLException {
        distinctZapisi = getConnection().prepareStatement("SELECT DISTINCT kod_zapisi, naimenovanie FROM \"DZH_zapisi\"");
        distinctSotrudniki = getConnection().prepareStatement("SELECT DISTINCT kod_sotrudnika, fio FROM \"DZH_sotrudniki\"");
        byData = getConnection().prepareStatement(q + " WHERE (g.data = ?)");
        byNameAndData = getConnection().prepareStatement(q + " WHERE (s.fio LIKE CONCAT('%', ?, '%')) AND (g.data = ?)");
    }

    @Override
    protected PreparedStatement createByIdStatement() throws SQLException {
        return getConnection().prepareStatement(q + " WHERE (kod_grafika = ?)");
    }

    @Override
    protected PreparedStatement createByNameStatement() throws SQLException {
        return getConnection().prepareStatement(q + " WHERE (s.fio LIKE CONCAT('%', ?, '%'))");
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
                " (kod_grafika, kod_sotrudnika, data, kod_zapisi1, vremya1, kod_zapisi2, vremya2, kod_zapisi3, vremya3)" +
                " VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    @Override
    protected PreparedStatement createUpdateStatement() throws SQLException {
        return getConnection().prepareStatement("UPDATE \"DZH_grafik_raboty\"" +
                " SET kod_sotrudnika = ?, data = ?, kod_zapisi1 = ?, vremya1 = ?, kod_zapisi2 = ?, vremya2 = ?, kod_zapisi3 = ?, vremya3 = ?" +
                " WHERE (kod_grafika = ?)");
    }

    public ResultSet queryDistinctZapisi() throws SQLException {
        return distinctZapisi.executeQuery();
    }

    public ResultSet queryDistinctSotrudniki() throws SQLException {
        return distinctSotrudniki.executeQuery();
    }

    public ResultSet queryByData(LocalDate date) throws SQLException {
        if (date == null) {
            return queryAll();
        }
        byData.setDate(1, Date.valueOf(date));
        return byData.executeQuery();
    }

    public ResultSet queryByNameAndData(String name, LocalDate date) throws SQLException {
        if (name.isEmpty()) {
            return queryByData(date);
        }
        if (date == null) {
            return queryByName(name);
        }
        byNameAndData.setString(1, name);
        byNameAndData.setDate(2, Date.valueOf(date));
        return byNameAndData.executeQuery();
    }
}
