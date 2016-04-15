package data_base;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SotrudnikDataBase extends BaseDataBase {

    private String q = "SELECT s.fio, s.vozrast, s.pol, s.adres, s.telefon, s.pasport, d.naimenovanie"
            + " FROM \"DZH_sotrudniki\" s, \"DZH_dolzhnosti\" d"
            + " WHERE (s.kod_dolzhnosti = d.kod_dolzhnosti) ";

    public SotrudnikDataBase() throws SQLException {}

    @Override
    protected PreparedStatement createByNameStatement() throws SQLException {
        return getConnection().prepareStatement(q + " AND (s.fio LIKE CONCAT('%', ?, '%'))");
    }

    @Override
    protected PreparedStatement createSelectAllStatement() throws SQLException {
       return getConnection().prepareStatement(q);
    }

}
