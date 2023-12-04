package edu.popovd;

import java.util.Random;

public abstract class TestCommons {

    public static final Random RANDOM = new Random();

    public static String getUniqString() {
        return getUniqString(16);
    }

    public static String getUniqString(int length) {
        String abc = "qwertyuiopasdfghjlkzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM12345678";
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < length; i++) {
            res.append(abc.charAt(RANDOM.nextInt(0, abc.length())));
        }
        return res.toString();
    }
}
