package panel;

import data_base.BaseDataBase;
import data_base.DolzhnostDataBase;
import table_model.BaseTableModel;
import table_model.DolzhnostiTableModel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DolzhnostiPanel extends BasePanel {

    private JTextField jOkladTextField;

    public DolzhnostiPanel() throws SQLException {
        jOkladTextField = new JTextField();
        jOkladTextField.setBounds(10, 30, 250, 30);
        jOkladTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTable();
            }
        });
        add(jOkladTextField);
    }

    @Override
    protected BaseTableModel createTableModel() {
        return new DolzhnostiTableModel();
    }

    @Override
    protected BaseDataBase createDataBase() throws SQLException {
        return new DolzhnostDataBase();
    }

    @Override
    protected void updateTable() {
        try {
            String name = getSearchTextField().getText();
            int oklad = getOkladInteger(jOkladTextField.getText());
            ResultSet resultSet = ((DolzhnostDataBase) getDataBase()).queryByNameOklad(name, oklad);
            getTableModel().fromQuery(resultSet);
            getTable().updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doClear() {
        jOkladTextField.setText("");
        super.doClear();
    }

    private int getOkladInteger(String okladString) {
        try {
            return Integer.parseInt(okladString);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return 0;
    }
}
