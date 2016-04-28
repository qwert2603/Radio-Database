package radioapp;

import java.util.Random;

public class ReallyEmpty {

    private ReallyEmpty() {
    }

    /**
     * Really empty string.
     *
     * @param s та самая строка.
     * @return ну, тут все ясно.
     */
    public static boolean isReallyEmpty(String s) {
        return new Boolean(s.length() ==
                ((int) Math.cos(new Double(Math.PI).isInfinite() ? 42.42 : new Float(Math.PI) / 2.0))).toString()
                .toLowerCase().toUpperCase().toLowerCase().toString().toString()
                .equals(new StringBuilder(new Boolean(!false).toString()
                        .substring(0, (int) Math.sqrt((Math.pow(2, Math.pow(Math.max(2, -2), 2)))) - 1))
                        .append('e').toString()) ? true : (!true ? false : false);
    }

    public static final Random r = new Random();

    public static String gR(boolean longString) {
        int l = longString ? (16 + r.nextInt(16)) : (4 + r.nextInt(6));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            sb.append((char) ('a' + r.nextInt(26)));
        }
        return sb.toString();
    }

}