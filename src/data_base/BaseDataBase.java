package data_base;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class BaseDataBase {

    private final Connection c;
    private PreparedStatement byNameStatement;
    private PreparedStatement allStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement insertStatement;

    public BaseDataBase() throws SQLException {
        //c = DriverManager.getConnection("jdbc:postgresql://data.biysk.secna.ru:5432/test", "test", "Aigee9");
        c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "test", "qwert");
    }

    protected final Connection getConnection() {
        return c;
    }

    protected abstract PreparedStatement createByNameStatement() throws SQLException;

    protected abstract PreparedStatement createSelectAllStatement() throws SQLException;

    protected abstract PreparedStatement createDeleteStatement() throws SQLException;

    protected abstract PreparedStatement createInsertStatement() throws SQLException;

    public ResultSet queryByName(String q) throws SQLException {
        if (q.isEmpty()) {
            return queryAll();
        }
        if (byNameStatement == null) {
            byNameStatement = createByNameStatement();
        }
        byNameStatement.setString(1, q);
        return byNameStatement.executeQuery();
    }

    public ResultSet queryAll() throws SQLException {
        if (allStatement == null) {
            allStatement = createSelectAllStatement();
        }
        return allStatement.executeQuery();
    }

    public void deleteById(int id) throws SQLException {
        if (deleteStatement == null) {
            deleteStatement = createDeleteStatement();
        }
        deleteStatement.setInt(1, id);
        deleteStatement.execute();
    }

    public void insertNew(List<String> args) throws SQLException {
        if (insertStatement == null) {
            insertStatement = createInsertStatement();
        }
        for (int i = 0; i < args.size(); i++) {
            String s = args.get(i);
            boolean done = false;
            if (s.isEmpty()) {
                insertStatement.setNull(i + 1, 0);
                done = true;
            }
            if (!done) {
                try {
                    LocalTime date = LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
                    insertStatement.setTime(i + 1, Time.valueOf(date));
                    done = true;
                } catch (Exception ignored) {
                }
            }
            if (!done) {
                try {
                    LocalDate date = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
                    insertStatement.setDate(i + 1, Date.valueOf(date));
                    done = true;
                } catch (Exception ignored) {
                }
            }
            if (!done) {
                try {
                    insertStatement.setInt(i + 1, Integer.parseInt(s));
                    done = true;
                } catch (NumberFormatException ignored) {
                }
            }
            if (!done) {
                insertStatement.setString(i + 1, s);
            }
        }
        insertStatement.execute();
    }

}
