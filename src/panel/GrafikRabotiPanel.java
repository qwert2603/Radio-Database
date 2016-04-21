package panel;

import arg_component.ResultSetComboBox;
import data_base.BaseDataBase;
import data_base.GrafikRabotiDataBase;
import table_model.BaseTableModel;
import table_model.GrafikRabotiTableModel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GrafikRabotiPanel extends BasePanel {

    private JLabel jDataSearchLabel;
    private JTextField jDataTextField;

    public GrafikRabotiPanel() throws SQLException {
        jDataSearchLabel = new JLabel("Фильтрация по дате:");
        jDataSearchLabel.setBounds(10, 5, 200, 30);
        add(jDataSearchLabel);

        jDataTextField = new JTextField();
        jDataTextField.setBounds(10, 30, 200, 30);
        jDataTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTable();
            }
        });
        add(jDataTextField);

        getArgComponentList().set(6, new ZapisiComboBox());
        getArgComponentList().set(4, new ZapisiComboBox());
        getArgComponentList().set(2, new ZapisiComboBox());

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

    @Override
    public void updateTable() {
        try {
            String name = getSearchTextField().getText();
            LocalDate date = getDate(jDataTextField.getText());
            ResultSet resultSet = ((GrafikRabotiDataBase) getDataBase()).queryByNameAndData(name, date);
            getTableModel().fromQuery(resultSet);
            getTable().updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doClearSearch() {
        jDataTextField.setText("");
        super.doClearSearch();
    }

    private static LocalDate getDate(String s) {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception ignored) {
        }
        return null;
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
