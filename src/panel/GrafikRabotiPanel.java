package panel;

import arg_component.ResultSetComboBox;
import data_base.BaseDataBase;
import data_base.GrafikRabotiDataBase;
import table_model.BaseTableModel;
import table_model.GrafikRabotiTableModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GrafikRabotiPanel extends BasePanel {

    public GrafikRabotiPanel() throws SQLException {
        getArgComponentList().set(5, new ZapisiComboBox());
        getArgComponentList().set(3, new ZapisiComboBox());
        getArgComponentList().set(1, new ZapisiComboBox());

        getArgComponentList().set(0, new ResultSetComboBox() {
            @Override
            protected ResultSet createResultSet() throws SQLException {
                return ((GrafikRabotiDataBase) getDataBase()).queryDistinctSotrudniki();
            }
        });
    }

    @Override
    protected BaseTableModel createTableModel() {
        return new GrafikRabotiTableModel();
    }

    @Override
    protected BaseDataBase createDataBase() throws SQLException {
        return new GrafikRabotiDataBase();
    }

    @Override
    protected String getSearchLabelTextSuffix() {
        return "фио сотрудника";
    }

    @Override
    protected String getEntityName() {
        return "график работы";
    }

    private class ZapisiComboBox extends ResultSetComboBox {
        public ZapisiComboBox() throws SQLException {
        }

        @Override
        protected ResultSet createResultSet() throws SQLException {
            return ((GrafikRabotiDataBase) getDataBase()).queryDistinctZapisi();
        }
    }
}
