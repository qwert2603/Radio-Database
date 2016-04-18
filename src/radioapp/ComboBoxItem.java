package radioapp;

public class ComboBoxItem {
    public ComboBoxItem(int id, String s) {
        this.s = s;
        this.id = id;
    }

    public String s;
    public int id;

    @Override
    public String toString() {
        return s;
    }
}
