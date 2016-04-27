package panel;

import arg_component.ArgComponent;
import arg_component.ArgTextField;
import data_base.BaseDataBase;
import radioapp.RadioFrame;
import table_model.BaseTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePanel extends JPanel {

    private BaseTableModel tableModel;
    private BaseDataBase dataBase;

    private JLabel jNameSearchLabel;
    private JTable jTable;
    private JTextField jSearchTextField;
    private JButton jClearButton;
    private JScrollPane jScrollPane;

    private JButton jDeleteButton;
    private JTextField jDeletingIdTextField;

    private JButton jInsertButton;
    private List<ArgComponent> jArgsComponentList = new ArrayList<ArgComponent>() {
        @Override
        public ArgComponent set(int index, ArgComponent element) {
            Component oldComponent = (Component) get(index);
            Component newComponent = (Component) element;
            newComponent.setBounds(oldComponent.getBounds());
            BasePanel.this.remove(oldComponent);
            BasePanel.this.add(newComponent);
            return super.set(index, element);
        }
    };

    protected abstract BaseTableModel createTableModel();

    protected abstract BaseDataBase createDataBase() throws SQLException;

    protected abstract String getSearchLabelTextSuffix();

    protected abstract String getEntityName();

    public BasePanel() throws SQLException {
        tableModel = createTableModel();
        dataBase = createDataBase();
        setLayout(null);

        tableModel.fromQuery(dataBase.queryAll());

        jTable = new JTable(tableModel);

        jNameSearchLabel = new JLabel(String.format("Фильтрация по %s:", getSearchLabelTextSuffix()));
        jNameSearchLabel.setBounds(280, 5, 400, 30);
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
        jClearButton.addActionListener(ev -> doClearSearch());
        add(jClearButton);

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(10, 70, 950, 460);
        add(jScrollPane);

        jDeleteButton = new JButton(String.format("Удалить %s с id = ", getEntityName()));
        jDeleteButton.setBounds(10, 540, 220, 30);
        jDeleteButton.setEnabled(false);
        jDeleteButton.addActionListener(ev -> doDelete());
        add(jDeleteButton);

        jDeletingIdTextField = new JTextField();
        jDeletingIdTextField.setBounds(250, 540, 200, 30);
        jDeletingIdTextField.addKeyListener(new KeyAdapter() {
            @SuppressWarnings("ResultOfMethodCallIgnored")
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    Integer.parseInt(jDeletingIdTextField.getText());
                    jDeleteButton.setEnabled(true);
                } catch (NumberFormatException e1) {
                    jDeleteButton.setEnabled(false);
                }
            }
        });
        add(jDeletingIdTextField);

        int argsCount = tableModel.getColumnCount() - 1;
        int widthOne = jScrollPane.getWidth() / (argsCount + 1);

        jInsertButton = new JButton("Вставить");
        jInsertButton.setBounds(10, 580, widthOne - 2, 30);
        jInsertButton.addActionListener(ev -> doInsert());
        add(jInsertButton);

        for (int i = 0; i < argsCount; i++) {
            ArgTextField argTextField = new ArgTextField();
            argTextField.setBounds(10 + (i + 1) * widthOne, 580, widthOne - 2, 30);
            add(argTextField);
            jArgsComponentList.add(argTextField);
        }
    }

    public void updateTable() {
        try {
            String name = jSearchTextField.getText();
            ResultSet resultSet = dataBase.queryByName(name);
            tableModel.fromQuery(resultSet);
            jTable.updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePanel() {
        updateTable();
        for (ArgComponent argComponent : jArgsComponentList) {
            try {
                argComponent.fill();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doClearSearch() {
        jSearchTextField.setText("");
        updateTable();
    }

    private void doDelete() {
        try {
            getDataBase().deleteById(Integer.parseInt(jDeletingIdTextField.getText()));
            RadioFrame.sRadioFrame.updateAllPanels();
            jDeletingIdTextField.setText("");
            jDeleteButton.setEnabled(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void doInsert() {
        try {
            List<String> args = new ArrayList<>();
            for (ArgComponent argComponent : jArgsComponentList) {
                args.add(argComponent.getValue());
            }
            dataBase.insertNew(args);
            for (ArgComponent argComponent : jArgsComponentList) {
                argComponent.clear();
            }
            RadioFrame.sRadioFrame.updateAllPanels();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(BasePanel.this, e.getMessage());
            e.printStackTrace();
        }
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

    public JTextField getSearchTextField() {
        return jSearchTextField;
    }

    public List<ArgComponent> getArgComponentList() {
        return jArgsComponentList;
    }
}
