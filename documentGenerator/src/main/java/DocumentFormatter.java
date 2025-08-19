import java.util.List;
import java.util.stream.Collectors;

public class DocumentFormatter {
    private static final int CPF_LENGTH = 11;
    private static final int CNPJ_LENGTH = 14;

    public static String format(List<Character> chars, boolean mask) {
        var raw = chars.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        if (!mask) {
            return raw;
        }

        if (chars.size() == CPF_LENGTH) {
            return String.format("%s.%s.%s-%s",
                    raw.substring(0, 3), raw.substring(3, 6),
                    raw.substring(6, 9), raw.substring(9, 11));
        }

        if (chars.size() == CNPJ_LENGTH) {
            return String.format("%s.%s.%s/%s-%s",
                    raw.substring(0, 2), raw.substring(2, 5),
                    raw.substring(5, 8), raw.substring(8, 12),
                    raw.substring(12, 14));
        }
        throw new IllegalArgumentException("Unable to format document with length " + chars.size());
    }

}
