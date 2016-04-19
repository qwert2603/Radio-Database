package panel;

import arg_component.ArgComboBox;
import arg_component.PolComboBox;
import data_base.BaseDataBase;
import data_base.SotrudnikDataBase;
import table_model.BaseTableModel;
import table_model.SotrudnikiTableModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SotrudnikiPanel extends BasePanel {

    public SotrudnikiPanel() throws SQLException {
        getArgComponentList().set(6, new ArgComboBox() {
            @Override
            protected ResultSet createArgsSet() throws SQLException {
                return ((SotrudnikDataBase) getDataBase()).queryDistinctDolzhnosti();
            }
        });

        getArgComponentList().set(2, new PolComboBox());
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

    @Override
    protected String getEntityName() {
        return "сотрудника";
    }
}
