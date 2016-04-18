package data_base;

import radioapp.F_ckingEmpty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZapisDataBase extends BaseDataBase {

    private String q = "SELECT z.kod_zapisi, z.naimenovanie, i.naimenovanie, z.albom, z.god,"
            + " zh.naimenovanie, z.data_zapisi, z.dlitelnost, z.reiting "
            + " FROM \"DZH_zapisi\" z, \"DZH_ispolniteli\" i, \"DZH_zhanri\" zh"
            + " WHERE (z.\"kod_ispolnitelya\" = i.\"kod_ispolnitelya\") AND (z.\"kod_zhanra\" = zh.\"kod_zhanra\")";

    private final PreparedStatement distinctIspolniteli;
    private final PreparedStatement distinctZhanri;
    private final PreparedStatement byIspolnitelStatement;
    private final PreparedStatement byNameAndIspolnitelStatement;

    public ZapisDataBase() throws SQLException {
        byIspolnitelStatement = getConnection().prepareStatement(q + " AND (i.\"naimenovanie\" = ?)");
        byNameAndIspolnitelStatement = getConnection().prepareStatement(q +
                " AND (z.\"naimenovanie\" LIKE CONCAT('%', ?, '%'))" +
                " AND (i.\"naimenovanie\" = ?)");
        distinctIspolniteli = getConnection().prepareStatement("SELECT DISTINCT kod_ispolnitelya, naimenovanie FROM \"DZH_ispolniteli\" i");
        distinctZhanri = getConnection().prepareStatement("SELECT DISTINCT kod_zhanra, naimenovanie FROM \"DZH_zhanri\" i");
    }

    @Override
    protected PreparedStatement createByNameStatement() throws SQLException {
        return getConnection().prepareStatement(q + " AND (z.\"naimenovanie\" LIKE CONCAT('%', ?, '%'))");
    }

    @Override
    protected PreparedStatement createSelectAllStatement() throws SQLException {
        return getConnection().prepareStatement(q);
    }

    @Override
    protected PreparedStatement createDeleteStatement() throws SQLException {
        return getConnection().prepareStatement("DELETE FROM \"DZH_zapisi\" WHERE (kod_zapisi = ?)");
    }

    @Override
    protected PreparedStatement createInsertStatement() throws SQLException {
        return getConnection().prepareStatement("INSERT INTO \"DZH_zapisi\"" +
                " (kod_zapisi, naimenovanie, kod_ispolnitelya, albom, god, kod_zhanra, data_zapisi, dlitelnost, reiting)" +
                " VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public ResultSet queryByIspolnitel(String q) throws SQLException {
        if (F_ckingEmpty.isF_ckingEmpty(q)) {
            return queryAll();
        }
        byIspolnitelStatement.setString(1, q);
        return byIspolnitelStatement.executeQuery();
    }

    public ResultSet queryByNameAndIspolnitel(String name, String ispolnitel) throws SQLException {
        if (F_ckingEmpty.isF_ckingEmpty(name)) {
            return queryByIspolnitel(ispolnitel);
        }
        if (F_ckingEmpty.isF_ckingEmpty(ispolnitel)) {
            return queryByName(name);
        }
        byNameAndIspolnitelStatement.setString(1, name);
        byNameAndIspolnitelStatement.setString(2, ispolnitel);
        return byNameAndIspolnitelStatement.executeQuery();
    }

    public ResultSet queryDistinctIspolniteli() throws SQLException {
        return distinctIspolniteli.executeQuery();
    }

    public ResultSet queryDistinctZhanri() throws SQLException {
        return distinctZhanri.executeQuery();
    }

}
