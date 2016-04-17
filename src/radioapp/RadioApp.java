package radioapp;

public class RadioApp {

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            new RadioFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
