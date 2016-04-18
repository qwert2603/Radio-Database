package panel;

import data_base.BaseDataBase;
import data_base.SotrudnikDataBase;
import radioapp.ComboBoxItem;
import radioapp.RadioFrame;
import table_model.BaseTableModel;
import table_model.SotrudnikiTableModel;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SotrudnikiPanel extends BasePanel {

    private JComboBox<String> jPolArgComboBox;
    private JComboBox<ComboBoxItem> jDolzhnostArgComboBox;

    public SotrudnikiPanel() throws SQLException {
        JTextField textField_6 = getArgsTextFields().get(6);
        remove(textField_6);
        getArgsTextFields().remove(6);

        jDolzhnostArgComboBox = new JComboBox<>();
        jDolzhnostArgComboBox.setBounds(textField_6.getBounds());
        fillDolzhnostArgComboBoxItems();
        add(jDolzhnostArgComboBox);


        JTextField textField_2 = getArgsTextFields().get(2);
        remove(textField_2);
        getArgsTextFields().remove(2);

        jPolArgComboBox = new JComboBox<>();
        jPolArgComboBox.setBounds(textField_2.getBounds());
        jPolArgComboBox.addItem("");
        jPolArgComboBox.addItem("m");
        jPolArgComboBox.addItem("f");
        add(jPolArgComboBox);
    }

    private void fillDolzhnostArgComboBoxItems() throws SQLException {
        Object selected = jDolzhnostArgComboBox.getSelectedItem();
        jDolzhnostArgComboBox.removeAllItems();
        jDolzhnostArgComboBox.addItem(new ComboBoxItem(-1, ""));
        ResultSet resultSet = ((SotrudnikDataBase) getDataBase()).queryDistinctDolzhnosti();
        while (resultSet.next()) {
            ComboBoxItem comboBoxItem = new ComboBoxItem(resultSet.getInt(1), resultSet.getString(2));
            jDolzhnostArgComboBox.addItem(comboBoxItem);
            if (Objects.equals(selected, comboBoxItem)) {
                jDolzhnostArgComboBox.setSelectedItem(comboBoxItem);
            }
        }
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

    @Override
    public void updatePanel() {
        super.updatePanel();
        try {
            fillDolzhnostArgComboBoxItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doInsert() {
        try {
            List<String> args = new ArrayList<>();
            for (JTextField jTextField : getArgsTextFields()) {
                args.add(jTextField.getText());
            }
            args.add(2, jPolArgComboBox.getSelectedItem().toString());
            args.add(6, String.valueOf(((ComboBoxItem) jDolzhnostArgComboBox.getSelectedItem()).id));
            getDataBase().insertNew(args);
            for (JTextField jTextField : getArgsTextFields()) {
                jTextField.setText("");
            }
            jPolArgComboBox.setSelectedIndex(0);
            jDolzhnostArgComboBox.setSelectedIndex(0);
            RadioFrame.sRadioFrame.updateAllPanels();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
