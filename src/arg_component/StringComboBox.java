package arg_component;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public abstract class StringComboBox extends JComboBox<String> implements ArgComponent {

    public StringComboBox() throws SQLException {
        fill();
        setSelectedItem("");
    }

    protected abstract List<String> createStringList();

    @Override
    public void fill() throws SQLException {
        Object selected = getSelectedItem();
        removeAllItems();
        addItem("");
        for (String s : createStringList()) {
            addItem(s);
        }
        setSelectedItem(selected);
    }

    @Override
    public void clear() {
        setSelectedIndex(0);
    }

    @Override
    public String getValue() {
        return getSelectedItem().toString();
    }

    @Override
    public void setValue(String s) {
        for (int i = 0; i < getItemCount(); i++) {
            String itemAt = getItemAt(i);
            if (itemAt.equals(s)) {
                setSelectedItem(itemAt);
                break;
            }
        }
    }
}
