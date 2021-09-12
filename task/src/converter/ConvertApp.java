package converter;

import java.util.Scanner;

public class ConvertApp {
    private static final String PROMPT_MESSAGE =
            "Enter two numbers in format: {source base} {target base} (To quit type /exit)";

    private final Scanner sc = new Scanner(System.in);

    public void execute() {
        while (true) {
            System.out.println(PROMPT_MESSAGE);
            String input = sc.nextLine();
            if ("/exit".equals(input)) {
                break;
            }
            new Converter(input).execute();
        }
    }
}
