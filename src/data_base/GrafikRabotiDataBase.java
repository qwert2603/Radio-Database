package data_base;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GrafikRabotiDataBase extends BaseDataBase {

    private String q = "SELECT s.fio, z.naimenovanie, g.vremya1, z.naimenovanie, g.vremya2, z.naimenovanie, g.vremya3 "
            + " FROM \"DZH_grafik_raboty\" g, \"DZH_sotrudniki\" s, \"DZH_zapisi\" z"
            + " WHERE (g.kod_sotrudnika = s.kod_sotrudnika)"
            + " AND ((g.kod_zapisi1 = z.kod_zapisi)"
            + " OR (g.kod_zapisi2 = z.kod_zapisi)"
            + " OR (g.kod_zapisi3 = z.kod_zapisi))";

    public GrafikRabotiDataBase() throws SQLException {
    }

    @Override
    protected PreparedStatement createByNameStatement() throws SQLException {
        return getConnection().prepareStatement(q + " AND (s.fio LIKE CONCAT('%', ?, '%'))");
    }

    @Override
    protected PreparedStatement createSelectAllStatement() throws SQLException {
        return getConnection().prepareStatement(q);
    }

}
