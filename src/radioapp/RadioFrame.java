package radioapp;

import panel.BasePanel;
import panel.DolzhnostiPanel;
import panel.GrafikRabotiPanel;
import panel.IspolnitelPanel;
import panel.SotrudnikiPanel;
import panel.ZapisPanel;
import panel.ZhanrPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RadioFrame extends JFrame {

    public static RadioFrame sRadioFrame;

    private JTabbedPane tabbedPane;
    private List<BasePanel> panelList = new ArrayList<>();

    public RadioFrame() throws HeadlessException, SQLException {
        super("Radio");
        setBounds(80, 40, 1200, 710);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 1170, 650);

        panelList.add(new ZapisPanel());
        panelList.add(new SotrudnikiPanel());
        panelList.add(new ZhanrPanel());
        panelList.add(new DolzhnostiPanel());
        panelList.add(new GrafikRabotiPanel());
        panelList.add(new IspolnitelPanel());

        String[] names = {"Записи", "Сотрудники", "Жанры", "Должности", "График работы", "Исполнители"};

        for (int i = 0; i < names.length; i++) {
            tabbedPane.addTab(names[i], panelList.get(i));
        }

        add(tabbedPane);
        setVisible(true);

        sRadioFrame = RadioFrame.this;
    }

    public void updateAllPanels() {
        for (BasePanel basePanel : panelList) {
            basePanel.updatePanel();
        }
    }

    public void showAndSelectIspolnitel(String name) {
        tabbedPane.setSelectedIndex(5);
        BasePanel panel = panelList.get(5);
        for (int i = 0; i < panel.getTableModel().getRowCount(); i++) {
            if (Objects.equals(String.valueOf(panel.getTableModel().getValueAt(i, 1)), name)) {
                panel.getTable().changeSelection(i, 1, false, false);
                break;
            }
        }
    }

    public void showAndSelectZharn(String name) {
        tabbedPane.setSelectedIndex(2);
        BasePanel panel = panelList.get(2);
        for (int i = 0; i < panel.getTableModel().getRowCount(); i++) {
            if (Objects.equals(String.valueOf(panel.getTableModel().getValueAt(i, 1)), name)) {
                panel.getTable().changeSelection(i, 1, false, false);
                break;
            }
        }
    }
}
