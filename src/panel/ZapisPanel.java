package panel;

import data_base.BaseDataBase;
import data_base.ZapisDataBase;
import table_model.BaseTableModel;
import table_model.ZapisTableModel;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZapisPanel extends BasePanel {

    private JLabel jIspolnitelFilterLabel;
    private JComboBox<String> jComboBox;

    public ZapisPanel() throws SQLException {
        jIspolnitelFilterLabel = new JLabel("Фильтрация по испонителю:");
        jIspolnitelFilterLabel.setBounds(10, 5, 200, 30);
        add(jIspolnitelFilterLabel);

        jComboBox = new JComboBox<>();
        jComboBox.setBounds(10, 30, 200, 30);
        jComboBox.addItem("");
        ResultSet resultSet = ((ZapisDataBase) getDataBase()).queryDistinctIspolniteli();
        while (resultSet.next()) {
            jComboBox.addItem(resultSet.getString(1));
        }
        jComboBox.addActionListener(ev -> updateTable());
        add(jComboBox);
    }

    @Override
    protected BaseTableModel createTableModel() {
        return new ZapisTableModel();
    }

    @Override
    protected BaseDataBase createDataBase() throws SQLException {
        return new ZapisDataBase();
    }

    @Override
    public void updateTable() {
        try {
            String name = getSearchTextField().getText();
            String ispolnitel = jComboBox.getSelectedItem().toString();
            ResultSet resultSet = ((ZapisDataBase) getDataBase()).queryByNameAndIspolnitel(name, ispolnitel);
            getTableModel().fromQuery(resultSet);
            getTable().updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doClear() {
        jComboBox.setSelectedIndex(0);
        super.doClear();
    }

    @Override
    protected String getSearchLabelTextSuffix() {
        return "названию записи";
    }

    @Override
    protected String getEntityName() {
        return "запись";
    }
}
