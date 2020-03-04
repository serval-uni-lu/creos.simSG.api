package duc.aintea.loadapproximation.test.utils;

import duc.aintea.utils.BaseTransform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BaseTransformTest {

    private static Arguments[] values() {
        int[] values = new int[]{18, 10254, 5874, Integer.MAX_VALUE, Integer.MIN_VALUE};
        int []sizes = new int[]{2, 5, 10, Integer.SIZE};

        var args = new Arguments[values.length * sizes.length];


        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < sizes.length; j++) {
                args[i*sizes.length + j] = Arguments.of(values[i], sizes[j]);
            }
        }

        return args;
    }

    private String buildExpected(int value, int size) {
        var expected = Integer.toBinaryString(value);

        if(expected.length() > size) {
            expected = expected.substring(expected.length() - size);
        } else if(expected.length() < size) {
            var missing = size - expected.length();
            expected = "0".repeat(missing) + expected;
        }

        return expected;

    }

//    @Test
//    public void test() {
//        System.out.println(Arrays.toString(BaseTransform.toBinary(8, 7)));
//    }


    @ParameterizedTest
    @MethodSource("values")
    public void toBinaryTest(int value, int size) {
        boolean[] actual = BaseTransform.toBinary(value, size);
        var strBuilder = new StringBuilder(actual.length);
        for (var b: actual) {
            if(b) {
                strBuilder.append('1');
            } else {
                strBuilder.append('0');
            }
        }

        var expected = buildExpected(value, size);

        Assertions.assertEquals(expected, strBuilder.toString());
    }



}
