import java.util.List;

public class CheckDigitCalculator {

    public static void calculate(List<Integer> digits) {
        for(int iteration = 0; iteration < 2; iteration++) {
            int sum = 0;
            int size = digits.size();

            for(int i = size - 1, weight = 2; i >= 0; i--) {
                sum += digits.get(i) * weight;
                weight = (weight == 9 ? 2 : weight + 1);
            }

            int dv = (sum % 11 < 2) ? 0 : 11 - (sum % 11);
            digits.add(dv);
        }
    }
}