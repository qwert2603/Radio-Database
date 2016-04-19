package arg_component;

import java.sql.SQLException;

public interface ArgComponent {
    void fill() throws SQLException;

    void clear();

    String getValue();
}
