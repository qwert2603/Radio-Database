package arg_component;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class ResultSetComboBox extends JComboBox<ComboBoxItem> implements ArgComponent {

    public ResultSetComboBox() throws SQLException {
        fill();
    }

    protected abstract ResultSet createResultSet() throws SQLException;

    @Override
    public void fill() throws SQLException {
        Object selected = getSelectedItem();
        removeAllItems();
        addItem(new ComboBoxItem(-1, ""));
        ResultSet resultSet = createResultSet();
        while (resultSet.next()) {
            ComboBoxItem comboBoxItem = new ComboBoxItem(resultSet.getInt(1), resultSet.getString(2));
            addItem(comboBoxItem);
            if (Objects.equals(selected, comboBoxItem)) {
                setSelectedItem(comboBoxItem);
            }
        }
    }

    @Override
    public void clear() {
        setSelectedIndex(0);
    }

    @Override
    public String getValue() {
        return String.valueOf(((ComboBoxItem) getSelectedItem()).id);
    }

    @Override
    public void setValue(String s) {
        for (int i = 0; i < getItemCount(); i++) {
            ComboBoxItem itemAt = getItemAt(i);
            if (itemAt.s.equals(s)) {
                setSelectedItem(itemAt);
                break;
            }
        }
    }
}
