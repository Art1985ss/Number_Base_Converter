package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

public class Converter {
    private static final String PROMPT_MESSAGE =
            "Enter number in base %s to convert to base %s (To go back type /back)";
    private static final String RESULT_MESSAGE = "Conversion result: %s";
    private final Scanner sc = new Scanner(System.in);
    private final int sourceBase;
    private final int targetBase;

    public Converter(String input) {
        String inputs[] = input.split(" ");
        this.sourceBase = Integer.parseInt(inputs[0]);
        this.targetBase = Integer.parseInt(inputs[1]);
    }

    public void execute() {
        while (true) {
            System.out.printf((PROMPT_MESSAGE) + "%n", sourceBase, targetBase);
            String input = sc.nextLine();
            if ("/back".equals(input)) {
                break;
            }
            String result;
            if (input.contains(".")) {
                result = fractionalConvert(input);
            } else {
                result = convert(input);
            }
            System.out.printf(RESULT_MESSAGE + "%n", result);
        }
    }

    private String fractionalConvert(String input) {
        String[] numbers = input.split("\\.");
        String integerPart = convert(numbers[0]);
        String fracPart = fractionToBase(fractionToDecimal(numbers[1]));
        return integerPart + "." + fracPart;

    }

    private String convert(String input) {
        BigInteger number = new BigInteger(input, sourceBase);
        return number.toString(targetBase);
    }

    private String fractionToBase(String decimalFraction) {
        BigInteger dr = BigInteger.valueOf(targetBase);
        BigInteger integer = new BigInteger(decimalFraction.toUpperCase());
        BigInteger denominator = BigInteger.valueOf((long) Math.pow(10, decimalFraction.length()));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            integer = integer.multiply(dr);
            int place = integer.divide(denominator).intValue();
            char ch = Character.forDigit(place, targetBase);
            sb.append(ch);
            integer = integer.mod(denominator);
            if (integer.compareTo(BigInteger.ZERO) == 0) {
                break;
            }
        }
        while (sb.length() < 5) {
            sb.append(0);
        }
        return sb.toString();
    }

    private String fractionToDecimal(String fraction) {
        if (fraction.equals("00000")) {
            return "0";
        }
        BigDecimal frac = BigDecimal.ZERO;
        BigDecimal multiplier = BigDecimal.valueOf(sourceBase);
        for (char ch : fraction.toCharArray()) {
            BigDecimal num = BigDecimal.valueOf(Character.digit(ch, sourceBase));
            frac = frac.add(
                num.divide(multiplier, 10, RoundingMode.HALF_UP)
            );
            multiplier = multiplier.multiply(BigDecimal.valueOf(sourceBase));
        }
        return frac.toString().substring(frac.toString().indexOf('.') + 1);
    }

}
