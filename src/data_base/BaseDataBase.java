package data_base;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class BaseDataBase {

    private static Connection sConnection = null;

    private PreparedStatement byIdStatement;
    private PreparedStatement byNameStatement;
    private PreparedStatement allStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;

    public BaseDataBase() throws SQLException {
        if (sConnection == null) {
            //sConnection = DriverManager.getConnection("jdbc:postgresql://data.biysk.secna.ru:5432/test", "test", "Aigee9");
            sConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "test", "1234");
        }
    }

    protected final Connection getConnection() {
        return sConnection;
    }

    protected abstract PreparedStatement createByIdStatement() throws SQLException;

    protected abstract PreparedStatement createByNameStatement() throws SQLException;

    protected abstract PreparedStatement createSelectAllStatement() throws SQLException;

    protected abstract PreparedStatement createDeleteStatement() throws SQLException;

    protected abstract PreparedStatement createInsertStatement() throws SQLException;

    protected abstract PreparedStatement createUpdateStatement() throws SQLException;

    public ResultSet queryById(int id) throws SQLException {
        if (byIdStatement == null) {
            byIdStatement = createByIdStatement();
        }
        byIdStatement.setInt(1, id);
        return byIdStatement.executeQuery();
    }

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

    public void updateBuId(int id, List<String> args) throws SQLException {
        if (updateStatement == null) {
            updateStatement = createUpdateStatement();
        }
        for (int i = 0; i < args.size(); i++) {
            String s = args.get(i);
            boolean done = false;
            if (s.isEmpty()) {
                updateStatement.setNull(i + 1, 0);
                done = true;
            }
            if (!done) {
                try {
                    LocalTime date = LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
                    updateStatement.setTime(i + 1, Time.valueOf(date));
                    done = true;
                } catch (Exception ignored) {
                }
            }
            if (!done) {
                try {
                    LocalDate date = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
                    updateStatement.setDate(i + 1, Date.valueOf(date));
                    done = true;
                } catch (Exception ignored) {
                }
            }
            if (!done) {
                try {
                    updateStatement.setInt(i + 1, Integer.parseInt(s));
                    done = true;
                } catch (NumberFormatException ignored) {
                }
            }
            if (!done) {
                updateStatement.setString(i + 1, s);
            }
        }
        updateStatement.setInt(args.size() + 1, id);
        updateStatement.execute();
    }

}
