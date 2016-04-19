package panel;

import arg_component.ResultSetComboBox;
import arg_component.StringComboBox;
import data_base.BaseDataBase;
import data_base.SotrudnikDataBase;
import table_model.BaseTableModel;
import table_model.SotrudnikiTableModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SotrudnikiPanel extends BasePanel {

    public SotrudnikiPanel() throws SQLException {
        getArgComponentList().set(6, new ResultSetComboBox() {
            @Override
            protected ResultSet createResultSet() throws SQLException {
                return ((SotrudnikDataBase) getDataBase()).queryDistinctDolzhnosti();
            }
        });

        getArgComponentList().set(2, new StringComboBox() {
            @Override
            protected List<String> createStringList() {
                return Arrays.asList("m", "f");
            }
        });
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
