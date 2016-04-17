package table_model;

public class SotrudnikiTableModel extends BaseTableModel {

    public SotrudnikiTableModel() {
        colNames.add("id");
        colNames.add("ФИО");
        colNames.add("Возраст");
        colNames.add("Пол");
        colNames.add("Адрес");
        colNames.add("Телефон");
        colNames.add("Паспорт");
        colNames.add("Должность");
    }

}