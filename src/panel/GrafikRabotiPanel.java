package panel;

import data_base.BaseDataBase;
import data_base.GrafikRabotiDataBase;
import radioapp.ComboBoxItem;
import radioapp.RadioFrame;
import table_model.BaseTableModel;
import table_model.GrafikRabotiTableModel;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GrafikRabotiPanel extends BasePanel {

    private JComboBox<ComboBoxItem> jSotrudnikArgComboBox;
    private JComboBox<ComboBoxItem> jZapis_1_ArgComboBox;
    private JComboBox<ComboBoxItem> jZapis_2_ArgComboBox;
    private JComboBox<ComboBoxItem> jZapis_3_ArgComboBox;

    public GrafikRabotiPanel() throws SQLException {
        JTextField textField_5 = getArgsTextFields().get(5);
        remove(textField_5);
        getArgsTextFields().remove(5);
        jZapis_3_ArgComboBox = new JComboBox<>();
        jZapis_3_ArgComboBox.setBounds(textField_5.getBounds());
        fillZapisArgComboBoxItems(jZapis_3_ArgComboBox);
        add(jZapis_3_ArgComboBox);


        JTextField textField_3 = getArgsTextFields().get(3);
        remove(textField_3);
        getArgsTextFields().remove(3);
        jZapis_2_ArgComboBox = new JComboBox<>();
        jZapis_2_ArgComboBox.setBounds(textField_3.getBounds());
        fillZapisArgComboBoxItems(jZapis_2_ArgComboBox);
        add(jZapis_2_ArgComboBox);

        JTextField textField_1 = getArgsTextFields().get(1);
        remove(textField_1);
        getArgsTextFields().remove(1);
        jZapis_1_ArgComboBox = new JComboBox<>();
        jZapis_1_ArgComboBox.setBounds(textField_1.getBounds());
        fillZapisArgComboBoxItems(jZapis_1_ArgComboBox);
        add(jZapis_1_ArgComboBox);

        JTextField textField_0 = getArgsTextFields().get(0);
        remove(textField_0);
        getArgsTextFields().remove(0);
        jSotrudnikArgComboBox = new JComboBox<>();
        jSotrudnikArgComboBox.setBounds(textField_0.getBounds());
        fillSotrudnikArgComboBoxItems();
        add(jSotrudnikArgComboBox);
    }

    private void fillZapisArgComboBoxItems(JComboBox<ComboBoxItem> jComboBox) throws SQLException {
        Object selected = jComboBox.getSelectedItem();
        jComboBox.removeAllItems();
        jComboBox.addItem(new ComboBoxItem(-1, ""));
        ResultSet resultSet = ((GrafikRabotiDataBase) getDataBase()).queryDistinctZapisi();
        while (resultSet.next()) {
            ComboBoxItem comboBoxItem = new ComboBoxItem(resultSet.getInt(1), resultSet.getString(2));
            jComboBox.addItem(comboBoxItem);
            if (Objects.equals(selected, comboBoxItem)) {
                jComboBox.setSelectedItem(comboBoxItem);
            }
        }
    }

    private void fillSotrudnikArgComboBoxItems() throws SQLException {
        Object selected = jSotrudnikArgComboBox.getSelectedItem();
        jSotrudnikArgComboBox.removeAllItems();
        jSotrudnikArgComboBox.addItem(new ComboBoxItem(-1, ""));
        ResultSet resultSet = ((GrafikRabotiDataBase) getDataBase()).queryDistinctSotrudniki();
        while (resultSet.next()) {
            ComboBoxItem comboBoxItem = new ComboBoxItem(resultSet.getInt(1), resultSet.getString(2));
            jSotrudnikArgComboBox.addItem(comboBoxItem);
            if (Objects.equals(selected, comboBoxItem)) {
                jSotrudnikArgComboBox.setSelectedItem(comboBoxItem);
            }
        }
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
    public void updatePanel() {
        try {
            fillSotrudnikArgComboBoxItems();
            fillZapisArgComboBoxItems(jZapis_1_ArgComboBox);
            fillZapisArgComboBoxItems(jZapis_2_ArgComboBox);
            fillZapisArgComboBoxItems(jZapis_3_ArgComboBox);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.updatePanel();
    }

    @Override
    protected void doInsert() {
        try {
            List<String> args = new ArrayList<>();
            for (JTextField jTextField : getArgsTextFields()) {
                args.add(jTextField.getText());
            }
            args.add(0, String.valueOf(((ComboBoxItem) jSotrudnikArgComboBox.getSelectedItem()).id));
            args.add(1, String.valueOf(((ComboBoxItem) jZapis_1_ArgComboBox.getSelectedItem()).id));
            args.add(3, String.valueOf(((ComboBoxItem) jZapis_2_ArgComboBox.getSelectedItem()).id));
            args.add(5, String.valueOf(((ComboBoxItem) jZapis_3_ArgComboBox.getSelectedItem()).id));
            getDataBase().insertNew(args);
            for (JTextField jTextField : getArgsTextFields()) {
                jTextField.setText("");
            }
            jSotrudnikArgComboBox.setSelectedIndex(0);
            jZapis_1_ArgComboBox.setSelectedIndex(0);
            jZapis_2_ArgComboBox.setSelectedIndex(0);
            jZapis_3_ArgComboBox.setSelectedIndex(0);
            RadioFrame.sRadioFrame.updateAllPanels();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
