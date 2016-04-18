package panel;

import data_base.BaseDataBase;
import data_base.ZapisDataBase;
import radioapp.ComboBoxItem;
import radioapp.RadioFrame;
import table_model.BaseTableModel;
import table_model.ZapisTableModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ZapisPanel extends BasePanel {

    private JLabel jIspolnitelFilterLabel;
    private JComboBox<String> jIspolnitelComboBox;
    private ActionListener comboBoxActionListener = ev -> updateTable();

    private JComboBox<ComboBoxItem> jIspolnitelArgComboBox;
    private JComboBox<ComboBoxItem> jZhanrArgComboBox;

    public ZapisPanel() throws SQLException {
        jIspolnitelFilterLabel = new JLabel("Фильтрация по испонителю:");
        jIspolnitelFilterLabel.setBounds(10, 5, 200, 30);
        add(jIspolnitelFilterLabel);
        jIspolnitelComboBox = new JComboBox<>();
        jIspolnitelComboBox.setBounds(10, 30, 200, 30);
        fillIspolnitelComboBoxItems();
        add(jIspolnitelComboBox);


        JTextField textField_4 = getArgsTextFields().get(4);
        remove(textField_4);
        getArgsTextFields().remove(4);
        jZhanrArgComboBox = new JComboBox<>();
        jZhanrArgComboBox.setBounds(textField_4.getBounds());
        fillZhanrArgComboBoxItems();
        add(jZhanrArgComboBox);


        JTextField textField_1 = getArgsTextFields().get(1);
        remove(textField_1);
        getArgsTextFields().remove(1);
        jIspolnitelArgComboBox = new JComboBox<>();
        jIspolnitelArgComboBox.setBounds(textField_1.getBounds());
        fillIspolnitelArgComboBoxItems();
        add(jIspolnitelArgComboBox);
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

    private void fillIspolnitelArgComboBoxItems() throws SQLException {
        Object selected = jIspolnitelArgComboBox.getSelectedItem();
        jIspolnitelArgComboBox.removeAllItems();
        jIspolnitelArgComboBox.addItem(new ComboBoxItem(-1, ""));
        ResultSet resultSet = ((ZapisDataBase) getDataBase()).queryDistinctIspolniteli();
        while (resultSet.next()) {
            ComboBoxItem comboBoxItem = new ComboBoxItem(resultSet.getInt(1), resultSet.getString(2));
            jIspolnitelArgComboBox.addItem(comboBoxItem);
            if (Objects.equals(selected, comboBoxItem)) {
                jIspolnitelArgComboBox.setSelectedItem(comboBoxItem);
            }
        }
    }

    private void fillZhanrArgComboBoxItems() throws SQLException {
        Object selected = jZhanrArgComboBox.getSelectedItem();
        jZhanrArgComboBox.removeAllItems();
        jZhanrArgComboBox.addItem(new ComboBoxItem(-1, ""));
        ResultSet resultSet = ((ZapisDataBase) getDataBase()).queryDistinctZhanri();
        while (resultSet.next()) {
            ComboBoxItem comboBoxItem = new ComboBoxItem(resultSet.getInt(1), resultSet.getString(2));
            jZhanrArgComboBox.addItem(comboBoxItem);
            if (Objects.equals(selected, comboBoxItem)) {
                jZhanrArgComboBox.setSelectedItem(comboBoxItem);
            }
        }
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
            fillIspolnitelArgComboBoxItems();
            fillZhanrArgComboBoxItems();
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

    @Override
    protected void doInsert() {
        try {
            List<String> args = new ArrayList<>();
            for (JTextField jTextField : getArgsTextFields()) {
                args.add(jTextField.getText());
            }
            args.add(1, String.valueOf(((ComboBoxItem) jIspolnitelArgComboBox.getSelectedItem()).id));
            args.add(4, String.valueOf(((ComboBoxItem) jZhanrArgComboBox.getSelectedItem()).id));
            getDataBase().insertNew(args);
            for (JTextField jTextField : getArgsTextFields()) {
                jTextField.setText("");
            }
            jIspolnitelArgComboBox.setSelectedIndex(0);
            jZhanrArgComboBox.setSelectedIndex(0);
            RadioFrame.sRadioFrame.updateAllPanels();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
