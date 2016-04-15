package panel;

import data_base.BaseDataBase;
import data_base.ZhanrDataBase;
import table_model.BaseTableModel;
import table_model.ZhanriTableModel;

import java.sql.SQLException;

public class ZhanrPanel extends BasePanel {

    public ZhanrPanel() throws SQLException {
    }

    @Override
    protected BaseTableModel createTableModel() {
        return new ZhanriTableModel();
    }

    @Override
    protected BaseDataBase createDataBase() throws SQLException {
        return new ZhanrDataBase();
    }

    @Override
    protected String getSearchLabelTextSuffix() {
        return "названию жанра";
    }
}
