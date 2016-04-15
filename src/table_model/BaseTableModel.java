package table_model;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.util.ArrayList;


public abstract class BaseTableModel extends AbstractTableModel {

    protected ArrayList<String> colNames;
    protected ArrayList<ArrayList<String>> cells;

    public BaseTableModel() {
        colNames = new ArrayList<>();
        cells = new ArrayList<>();
    }

    public void fromQuery(ResultSet s) {
        cells.clear();

        try {
            while (s.next()) {
                ArrayList<String> curRow = new ArrayList<>();
                for (int i = 1; i < colNames.size() + 1; i++) {
                    curRow.add(s.getString(i));
                }
                cells.add(curRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cells.clear();
        }
    }

    @Override
    public int getColumnCount() {
        return colNames.size();
    }

    @Override
    public int getRowCount() {
        return cells.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return cells.get(row).get(column);
    }

    @Override
    public String getColumnName(int column) {
        return colNames.get(column);
    }
}
