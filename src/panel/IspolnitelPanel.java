package panel;

import data_base.BaseDataBase;
import data_base.IspolnitelDataBase;
import table_model.IspolniteliTableModel;
import table_model.BaseTableModel;

import java.sql.SQLException;

public class IspolnitelPanel extends BasePanel {

    public IspolnitelPanel() throws SQLException {
    }

    @Override
    protected BaseTableModel createTableModel() {
        return new IspolniteliTableModel();
    }

    @Override
    protected BaseDataBase createDataBase() throws SQLException {
        return new IspolnitelDataBase();
    }

    @Override
    protected String getSearchLabelTextSuffix() {
        return "названию исполнителя";
    }

    @Override
    protected String getEntityName() {
        return "исполнителя";
    }
}
