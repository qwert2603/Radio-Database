package radioapp;

import panel.*;

import javax.swing.*;

public class RadioApp {

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            JFrame fr = new JFrame("Radio");
            fr.setBounds(100, 100, 800, 600);
            fr.setLayout(null);
            fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setBounds(10, 10, 770, 540);

            tabbedPane.addTab("Записи", new ZapisPanel());
            tabbedPane.addTab("Сотрудники", new SotrudnikiPanel());
            tabbedPane.addTab("Жанры", new ZhanrPanel());
            tabbedPane.addTab("Должности", new DolzhnostiPanel());
            tabbedPane.addTab("График работы", new GrafikRabotiPanel());
            tabbedPane.addTab("Исполнители", new IspolnitelPanel());

            fr.add(tabbedPane);
            fr.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
