package chess.presentation.view;

import java.util.Scanner;

public class InputView {

    private static final String START_COMMAND = "start";
    private static final String END_COMMAND = "end";
    private static final String CONTINUE_COMMAND = "yes";
    private static final String NEW_GAME_COMMAND = "no";

    private final Scanner scanner = new Scanner(System.in);

    public boolean readWannaPlay() {
        String input = readLine();
        return covertToBoolean(input, START_COMMAND, END_COMMAND);
    }

    public boolean readWannaContinue() {
        String input = readLine();
        return covertToBoolean(input, CONTINUE_COMMAND, NEW_GAME_COMMAND);
    }

    public String readLine() {
        return scanner.nextLine().trim();
    }

    private boolean covertToBoolean(String input, String yesCommand, String noCommand) {
        if (yesCommand.equals(input)) {
            return true;
        }

        if (noCommand.equals(input)) {
            return false;
        }

        String message = String.format("존재하지 않는 명령어입니다. %s와 %s 중 하나를 입력해주세요.", yesCommand, noCommand);
        throw new IllegalArgumentException(message);
    }
}
