package creos.simgsg.api.utils;

import java.util.function.BinaryOperator;

public class StringAccumlator implements BinaryOperator<String> {
    private StringAccumlator(){}

    public static final StringAccumlator INSTANCE = new StringAccumlator();


    @Override
    public String apply(String s1, String s2) {
        if(s1.equals("")) return s2;
        return s1 + ", " + s2;
    }
}
