package panel;

import arg_component.ArgComboBox;
import data_base.BaseDataBase;
import data_base.ZapisDataBase;
import table_model.BaseTableModel;
import table_model.ZapisTableModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ZapisPanel extends BasePanel {

    private JLabel jIspolnitelFilterLabel;
    private JComboBox<String> jIspolnitelComboBox;
    private ActionListener comboBoxActionListener = ev -> updateTable();

    public ZapisPanel() throws SQLException {
        jIspolnitelFilterLabel = new JLabel("Фильтрация по испонителю:");
        jIspolnitelFilterLabel.setBounds(10, 5, 200, 30);
        add(jIspolnitelFilterLabel);
        jIspolnitelComboBox = new JComboBox<>();
        jIspolnitelComboBox.setBounds(10, 30, 200, 30);
        fillIspolnitelComboBoxItems();
        add(jIspolnitelComboBox);

        getArgComponentList().set(4, new ArgComboBox() {
            @Override
            protected ResultSet createArgsSet() throws SQLException {
                return ((ZapisDataBase) getDataBase()).queryDistinctZhanri();
            }
        });

        getArgComponentList().set(1, new ArgComboBox() {
            @Override
            protected ResultSet createArgsSet() throws SQLException {
                return ((ZapisDataBase) getDataBase()).queryDistinctIspolniteli();
            }
        });
    }

    private void fillIspolnitelComboBoxItems() throws SQLException {
        Object selected = jIspolnitelComboBox.getSelectedItem();
        jIspolnitelComboBox.removeActionListener(comboBoxActionListener);
        jIspolnitelComboBox.removeAllItems();
        jIspolnitelComboBox.addItem("");
        ResultSet resultSet = ((ZapisDataBase) getDataBase()).queryDistinctIspolniteli();
        while (resultSet.next()) {
            String string = resultSet.getString(2);
            jIspolnitelComboBox.addItem(string);
            if (Objects.equals(selected, string)) {
                jIspolnitelComboBox.setSelectedItem(string);
            }
        }
        jIspolnitelComboBox.addActionListener(comboBoxActionListener);
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
            String ispolnitel = jIspolnitelComboBox.getSelectedItem().toString();
            ResultSet resultSet = ((ZapisDataBase) getDataBase()).queryByNameAndIspolnitel(name, ispolnitel);
            getTableModel().fromQuery(resultSet);
            getTable().updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePanel() {
        try {
            fillIspolnitelComboBoxItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.updatePanel();
    }

    @Override
    protected void doClearSearch() {
        jIspolnitelComboBox.setSelectedIndex(0);
        super.doClearSearch();
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
