package radioapp;

import panel.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RadioFrame extends JFrame {

    public static RadioFrame sRadioFrame;

    private List<BasePanel> panelList = new ArrayList<>();

    public RadioFrame() throws HeadlessException, SQLException {
        super("Radio");
        setBounds(100, 40, 800, 710);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 770, 650);

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

    public void updateAllUI() {
        for (BasePanel basePanel : panelList) {
            basePanel.updateTable();
        }
    }
}
