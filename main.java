import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashiraPlacements {

    public static void main(String[] args) {

        // Test Case 1
        Map<String, Object> testCase1 = new HashMap<>();
        Map<String, Integer> keys1 = new HashMap<>();
        keys1.put("n", 4);
        keys1.put("k", 3);
        testCase1.put("keys", keys1);
        Map<String, String> root1_1 = new HashMap<>();
        root1_1.put("base", "10");
        root1_1.put("value", "4");
        testCase1.put("1", root1_1);
        Map<String, String> root1_2 = new HashMap<>();
        root1_2.put("base", "2");
        root1_2.put("value", "111");
        testCase1.put("2", root1_2);
        Map<String, String> root1_3 = new HashMap<>();
        root1_3.put("base", "10");
        root1_3.put("value", "12");
        testCase1.put("3", root1_3);
        Map<String, String> root1_4 = new HashMap<>();
        root1_4.put("base", "4");
        root1_4.put("value", "213");
        testCase1.put("6", root1_4);

        // Test Case 2
        Map<String, Object> testCase2 = new HashMap<>();
        Map<String, Integer> keys2 = new HashMap<>();
        keys2.put("n", 10);
        keys2.put("k", 7);
        testCase2.put("keys", keys2);
        Map<String, String> root2_1 = new HashMap<>();
        root2_1.put("base", "6");
        root2_1.put("value", "13444211440455345511");
        testCase2.put("1", root2_1);
        Map<String, String> root2_2 = new HashMap<>();
        root2_2.put("base", "15");
        root2_2.put("value", "aed7015a346d635");
        testCase2.put("2", root2_2);
        Map<String, String> root2_3 = new HashMap<>();
        root2_3.put("base", "15");
        root2_3.put("value", "6aeeb69631c227c");
        testCase2.put("3", root2_3);
        Map<String, String> root2_4 = new HashMap<>();
        root2_4.put("base", "16");
        root2_4.put("value", "e1b5e05623d881f");
        testCase2.put("4", root2_4);
        Map<String, String> root2_5 = new HashMap<>();
        root2_5.put("base", "8");
        root2_5.put("value", "316034514573652620673");
        testCase2.put("5", root2_5);
        Map<String, String> root2_6 = new HashMap<>();
        root2_6.put("base", "3");
        root2_6.put("value", "2122212201122002221120200210011020220200");
        testCase2.put("6", root2_6);
        Map<String, String> root2_7 = new HashMap<>();
        root2_7.put("base", "3");
        root2_7.put("value", "20120221122211000100210021102001201112121");
        testCase2.put("7", root2_7);
        Map<String, String> root2_8 = new HashMap<>();
        root2_8.put("base", "6");
        root2_8.put("value", "20220554335330240002224253");
        testCase2.put("8", root2_8);
        Map<String, String> root2_9 = new HashMap<>();
        root2_9.put("base", "12");
        root2_9.put("value", "45153788322a1255483");
        testCase2.put("9", root2_9);
        Map<String, String> root2_10 = new HashMap<>();
        root2_10.put("base", "7");
        root2_10.put("value", "1101613130313526312514143");
        testCase2.put("10", root2_10);

        List<Map<String, Object>> allTestCases = List.of(testCase1, testCase2);

        for (int i = 0; i < allTestCases.size(); i++) {
            System.out.println("--- Running Test Case " + (i + 1) + " ---");
            BigInteger constantTerm = findConstantTerm(allTestCases.get(i));
            System.out.println("The constant term (c) is: " + constantTerm);
            System.out.println(); // Newline for separation
        }
    }

    @SuppressWarnings("unchecked")
    public static BigInteger findConstantTerm(Map<String, Object> input) {
        Map<String, Integer> keys = (Map<String, Integer>) input.get("keys");
        int k = keys.get("k");

        // Collect the x and y values for the k roots needed
        BigInteger[] x = new BigInteger[k];
        BigInteger[] y = new BigInteger[k];

        int currentIndex = 0;
        for (int i = 1; i <= 10; i++) {
            String key = String.valueOf(i);
            if (input.containsKey(key)) {
                if (currentIndex >= k) {
                    break;
                }
                Map<String, String> rootData = (Map<String, String>) input.get(key);
                String valueStr = rootData.get("value");
                int base = Integer.parseInt(rootData.get("base"));
                
                x[currentIndex] = BigInteger.valueOf(i);
                y[currentIndex] = new BigInteger(valueStr, base);
                currentIndex++;
            }
        }
        
        BigInteger c = BigInteger.ZERO;
        for (int j = 0; j < k; j++) {
            BigInteger yj = y[j];
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int i = 0; i < k; i++) {
                if (i != j) {
                    numerator = numerator.multiply(x[i].negate());
                    denominator = denominator.multiply(x[j].subtract(x[i]));
                }
            }
            c = c.add(yj.multiply(numerator.divide(denominator)));
        }
        return c;
    }
}