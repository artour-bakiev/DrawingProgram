package ab.dp;

import ab.dp.commands.CommandBuilder;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        final Scanner input = new Scanner(System.in);
        Canvas canvas = null;
        final CommandBuilder commandBuilder = new CommandBuilder();
        while (input.hasNextLine()) {
            final String userInput = input.nextLine();
            if (terminationSequence(userInput)) {
                break;
            }
            if (userInput.isEmpty()) {
                continue;
            }
            final Command command = commandBuilder.build(userInput);
            if (command == null) {
                showHelp(commandBuilder.hint());
                continue;
            }
            final Canvas result = command.run(canvas);
            if (result == null) {
                showHelp(commandBuilder.hint());
                continue;
            }
            canvas = result;
            canvas.draw();
        }
    }

    private static boolean terminationSequence(String userInput) {
        return userInput.length() == 1 && userInput.charAt(0) == 'Q';
    }

    private static void showHelp(String hint) {
        System.out.println(hint);
        System.out.flush();
    }
}
