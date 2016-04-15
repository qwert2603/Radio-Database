package panel;

import data_base.BaseDataBase;
import data_base.GrafikRabotiDataBase;
import table_model.GrafikRabotiTableModel;
import table_model.BaseTableModel;

import java.sql.SQLException;

public class GrafikRabotiPanel extends BasePanel {

    public GrafikRabotiPanel() throws SQLException {
    }

    @Override
    protected BaseTableModel createTableModel() {
        return new GrafikRabotiTableModel();
    }

    @Override
    protected BaseDataBase createDataBase() throws SQLException {
        return new GrafikRabotiDataBase();
    }
}
