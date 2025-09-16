import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper; // Import the Jackson library

public class HashiraPlacements {

    public static void main(String[] args) {
        try {
            String filePath = "input.json";
            
            // Use Jackson's ObjectMapper to parse the JSON file
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> testCase = mapper.readValue(new File(filePath), Map.class);
            
            BigInteger constantTerm = findConstantTerm(testCase);
            System.out.println("The constant term (c) is: " + constantTerm);

        } catch (IOException e) {
            System.err.println("Error reading or parsing the JSON file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
public static BigInteger findConstantTerm(Map<String, Object> input) {
    Map<String, Object> keys = (Map<String, Object>) input.get("keys");
    int k = (Integer) keys.get("k");

    BigInteger[] x = new BigInteger[k];
    BigInteger[] y = new BigInteger[k];

    int currentIndex = 0;
    // Iterate through all possible root keys
    for (String key : input.keySet()) {
        if (Character.isDigit(key.charAt(0))) {
            if (currentIndex >= k) break;

            Map<String, String> rootData = (Map<String, String>) input.get(key);
            String valueStr = rootData.get("value");
            int base = Integer.parseInt(rootData.get("base"));
            
            x[currentIndex] = new BigInteger(key);
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