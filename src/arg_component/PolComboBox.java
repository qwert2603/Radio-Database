package arg_component;

import javax.swing.*;
import java.sql.SQLException;


public class PolComboBox extends JComboBox<String> implements ArgComponent {

    public PolComboBox() throws SQLException {
        fill();
    }

    @Override
    public void fill() throws SQLException {
        Object selected = getSelectedItem();
        removeAllItems();
        addItem("");
        addItem("m");
        addItem("f");
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
}
