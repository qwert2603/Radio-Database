package panel;

import data_base.BaseDataBase;
import data_base.SotrudnikDataBase;
import table_model.SotrudnikiTableModel;
import table_model.BaseTableModel;

import java.sql.SQLException;

public class SotrudnikiPanel extends BasePanel {

    public SotrudnikiPanel() throws SQLException {
    }

    @Override
    protected BaseTableModel createTableModel() {
        return new SotrudnikiTableModel();
    }

    @Override
    protected BaseDataBase createDataBase() throws SQLException {
        return new SotrudnikDataBase();
    }

    @Override
    protected String getSearchLabelTextSuffix() {
        return "фио сотрудника";
    }
}
