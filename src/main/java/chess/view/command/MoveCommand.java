package chess.view.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import chess.domain.board.Coordinate;

public class MoveCommand {

    private static final int COMMAND_INDEX = 0;
    private static final int SOURCE_INDEX = 1;
    private static final int TARGET_INDEX = 2;
    private static final int FILE_INDEX = 0;
    private static final int RANK_INDEX = 1;
    private static final int SOURCE_DATA_INDEX = 0;
    private static final int TARGET_DATA_INDEX = 1;
    private static final String MOVE_COMMAND = "move";
    private static final String END_COMMAND = "end";
    private static final String STATUS_COMMAND = "status";

    private final boolean isEnd;
    private final boolean isStatus;
    private final List<Coordinate> data;

    private MoveCommand(boolean isEnd, boolean isStatus, List<Coordinate> data) {
        this.isEnd = isEnd;
        this.isStatus = isStatus;
        this.data = data;
    }

    public static MoveCommand from(String input) {
        if (END_COMMAND.equals(input)) {
            return new MoveCommand(true, false, Collections.emptyList());
        }

        if (STATUS_COMMAND.equals(input)) {
            return new MoveCommand(false, true, Collections.emptyList());
        }

        return createFillMoveCommand(input);
    }

    private static MoveCommand createFillMoveCommand(String input) {
        List<String> commandSegments = Arrays.asList(input.split(" "));
        validateCommandSegmentSize(commandSegments);
        validateCommand(commandSegments.get(COMMAND_INDEX));
        Coordinate source = createCoordinate(commandSegments.get(SOURCE_INDEX));
        Coordinate target = createCoordinate(commandSegments.get(TARGET_INDEX));

        return new MoveCommand(false, false, List.of(source, target));
    }

    private static void validateCommandSegmentSize(List<String> commandSegments) {
        if (commandSegments.size() != 3) {

            String message = String.format(
                    "잘못된 입력입니다. %s source target 형식으로 입력해주세요. ex) %s a2 a4",
                    MOVE_COMMAND,
                    MOVE_COMMAND
            );
            throw new IllegalArgumentException(message);
        }
    }

    private static void validateCommand(String command) {
        if (!MOVE_COMMAND.equals(command)) {
            String message = String.format(
                    "존재하지 않는 명령어입니다. %s, %s, %s 중 하나를 입력해주세요.",
                    MOVE_COMMAND,
                    END_COMMAND,
                    STATUS_COMMAND
            );
            throw new IllegalArgumentException(message);
        }
    }

    private static Coordinate createCoordinate(String input) {
        List<String> coordinateSegments = Arrays.asList(input.split(""));
        validateCoordinateSegmentSize(coordinateSegments);
        int rankValue = Integer.parseInt(coordinateSegments.get(RANK_INDEX));
        char fileValue = coordinateSegments.get(FILE_INDEX).charAt(0);

        return new Coordinate(rankValue, fileValue);
    }

    private static void validateCoordinateSegmentSize(List<String> coordinateSegments) {
        if (coordinateSegments.size() != 2) {
            throw new IllegalArgumentException("올바른 형식의 좌표를 입력해주세요. ex) a2");
        }
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public Coordinate source() {
        validateEmptyData();

        return data.get(SOURCE_DATA_INDEX);
    }

    public Coordinate target() {
        validateEmptyData();

        return data.get(TARGET_DATA_INDEX);
    }

    private void validateEmptyData() {
        if (data.isEmpty()) {
            throw new IllegalStateException("데이터가 존재하지 않습니다.");
        }
    }
}
