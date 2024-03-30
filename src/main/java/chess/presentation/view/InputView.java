package chess.presentation.view;

import java.util.Scanner;

public class InputView {

    private static final String START_COMMAND = "start";
    private static final String END_COMMAND = "end";

    private final Scanner scanner = new Scanner(System.in);

    // TODO: 메서드 길이 개선
    public boolean readWannaPlay() {
        String input = readLine();

        if (START_COMMAND.equals(input)) {
            return true;
        }

        if (END_COMMAND.equals(input)) {
            return false;
        }

        String message = String.format("존재하지 않는 명령어입니다. %s와 %s 중 하나를 입력해주세요.", START_COMMAND, END_COMMAND);
        throw new IllegalArgumentException(message);
    }

    public String readLine() {
        return scanner.nextLine().trim();
    }
}
