package radioapp;

public class ComboBoxItem {
    public ComboBoxItem(int id, String s) {
        this.s = s;
        this.id = id;
    }

    public String s;
    public int id;

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ComboBoxItem) && (id == ((ComboBoxItem) obj).id);
    }

    @Override
    public String toString() {
        return s;
    }
}
