package panel;

import data_base.BaseDataBase;
import table_model.BaseTableModel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BasePanel extends JPanel {

    private BaseTableModel tableModel;
    private BaseDataBase dataBase;

    private JLabel jNameSearchLabel;
    private JTable jTable;
    private JTextField jSearchTextField;
    private JButton jClearButton;
    private JScrollPane jScrollPane;

    protected abstract BaseTableModel createTableModel();

    protected abstract BaseDataBase createDataBase() throws SQLException;

    protected abstract String getSearchLabelTextSuffix();

    public BasePanel() throws SQLException {
        tableModel = createTableModel();
        dataBase = createDataBase();
        setLayout(null);

        tableModel.fromQuery(dataBase.queryAll());

        jTable = new JTable(tableModel);

        jNameSearchLabel = new JLabel(String.format("Поиск по %s:", getSearchLabelTextSuffix()));
        jNameSearchLabel.setBounds(280, 5, 200, 30);
        add(jNameSearchLabel);

        jSearchTextField = new JTextField("");
        jSearchTextField.setBounds(280, 30, 200, 30);
        jSearchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTable();
            }
        });
        add(jSearchTextField);

        jClearButton = new JButton("Очистить фильтр");
        jClearButton.setBounds(500, 30, 150, 30);
        jClearButton.addActionListener(ev -> doClear());
        add(jClearButton);

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(10, 70, 750, 460);
        add(jScrollPane);
    }

    protected void updateTable() {
        try {
            String name = jSearchTextField.getText();
            ResultSet resultSet = dataBase.queryByName(name);
            tableModel.fromQuery(resultSet);
            jTable.updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doClear() {
        jSearchTextField.setText("");
        updateTable();
    }

    public BaseTableModel getTableModel() {
        return tableModel;
    }

    public BaseDataBase getDataBase() {
        return dataBase;
    }

    public JTable getTable() {
        return jTable;
    }

    public JScrollPane getScrollPane() {
        return jScrollPane;
    }

    public JButton getClearButton() {
        return jClearButton;
    }

    public JTextField getSearchTextField() {
        return jSearchTextField;
    }

    public JLabel getNameSearchLabel() {
        return jNameSearchLabel;
    }
}
