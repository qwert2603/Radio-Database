package arg_component;

import javax.swing.*;

public class ArgTextField extends JTextField implements ArgComponent {

    @Override
    public void fill() {
        // nth
    }

    @Override
    public void clear() {
        setText("");
    }

    @Override
    public String getValue() {
        return getText();
    }

}
