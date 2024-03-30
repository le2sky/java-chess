package chess.presentation.view;

import java.util.Scanner;

public class InputView {

    private static final String START_COMMAND = "start";
    private static final String END_COMMAND = "end";
    private static final String CONTINUE_COMMAND = "yes";
    private static final String NEW_GAME_COMMAND = "no";

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

    // TODO: 메서드 길이 개선
    public boolean readWannaContinue() {
        System.out.println("> 이전에 진행하던 게임이 존재합니다. 불러오겠습니까?");
        System.out.println("> 이전 계속 진행 : yes");
        System.out.println("> 새로운 게임 시작 : no");
        String input = readLine();

        if (CONTINUE_COMMAND.equals(input)) {
            return true;
        }

        if (NEW_GAME_COMMAND.equals(input)) {
            return false;
        }

        String message = String.format(
                "존재하지 않는 명령어입니다. %s와 %s 중 하나를 입력해주세요.",
                CONTINUE_COMMAND,
                NEW_GAME_COMMAND
        );

        throw new IllegalArgumentException(message);
    }

    public String readLine() {
        return scanner.nextLine().trim();
    }
}
