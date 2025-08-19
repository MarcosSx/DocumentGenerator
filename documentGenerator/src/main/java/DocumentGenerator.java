import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DocumentGenerator {
    private static final String ALPHA_NUMERIC_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static List<Character> generateAlphaNumericList(int length) {
        SecureRandom random = new SecureRandom();
        List<Character> result = new ArrayList<>(length);

        random.ints(length, 0, ALPHA_NUMERIC_STRING.length())
                .forEach(i -> result.add(ALPHA_NUMERIC_STRING.charAt(i)));

        return result;
    }

    public static List<Integer> generateTaxIdRoot(DocumentType type) {
        List<Integer> root = new ArrayList<>();

        if(type == DocumentType.CPF) {
            for(int i = 0; i < 9; i++) {
                root.add(ThreadLocalRandom.current().nextInt(0, 10));
            }
        }
        else if(type == DocumentType.CNPJ) {
            for(int i = 0; i < 8; i++) {
                root.add(ThreadLocalRandom.current().nextInt(0, 10));
            }
            root.add(0); root.add(0); root.add(0); // raiz fixa
            root.add(1);
        }

        return root;
    }

    public static String generate(DocumentType type, boolean mask) {
        List<Integer> digits;

        if(type == DocumentType.CNPJ_ALPHA) {
            List<Character> alpha = generateAlphaNumericList(12);
            digits = toIntList(alpha);
        }
        else {
            digits = generateTaxIdRoot(type);
        }

        CheckDigitCalculator.calculate(digits);

        List<Character> result = toCharList(digits);
        return DocumentFormatter.format(result, mask);
    }
    public static String generateFromRoot(String root, boolean mask) {
        List<Character> chars = root.toUpperCase()
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        if(chars.size() != 12) {
            throw new IllegalArgumentException("Root length must be 12");
        }

        List<Integer> digits = toIntList(chars);
        CheckDigitCalculator.calculate(digits);
        return DocumentFormatter.format(toCharList(digits), mask);
    }

    private static List<Integer> toIntList(List<Character> chars) {
        var formattedList = new ArrayList<Integer>();
        for (Character c : chars) {
            formattedList.add((int) c - 48);
        }
        return formattedList;
    }

    private static List<Character> toCharList(List<Integer> ints) {
        var formattedList = new ArrayList<Character>();
        for (int i : ints) {
            formattedList.add((char) (i + 48));
        }
        return formattedList;
    }

    public static void main(String[] args) {
        System.out.println(DocumentGenerator.generate(DocumentType.CPF, false));
        System.out.println(DocumentGenerator.generate(DocumentType.CNPJ, false));
        System.out.println(DocumentGenerator.generate(DocumentType.CNPJ_ALPHA, false));
        System.out.println(DocumentGenerator.generateFromRoot("ABC5413S0011", true));
    }

}
