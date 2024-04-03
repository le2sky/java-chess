package chess.presentation.controller.command;

import java.util.Arrays;
import java.util.List;
import chess.application.request.MovePieceRequest;
import chess.domain.piece.Coordinate;

class MoveCommand implements Command {

    private static final int COMMAND_INDEX = 0;
    private static final int SOURCE_INDEX = 1;
    private static final int TARGET_INDEX = 2;
    private static final int FILE_INDEX = 0;
    private static final int RANK_INDEX = 1;
    private static final String MOVE_COMMAND = "move";
    private static final String SEGMENT_SPLIT_DELIMITER = " ";

    private final MovePieceRequest data;

    public MoveCommand(String input) {
        validate(input);
        this.data = createData(input);
    }

    private void validate(String input) {
        if (!isValidSegmentFormat(input)) {
            String message = String.format(
                    "잘못된 입력입니다. %s source target 형식으로 입력해주세요. ex) %s a2 a4",
                    MOVE_COMMAND,
                    MOVE_COMMAND
            );

            throw new IllegalArgumentException(message);
        }
    }

    private boolean isValidSegmentFormat(String input) {
        List<String> commandSegments = Arrays.asList(input.split(SEGMENT_SPLIT_DELIMITER));

        return commandSegments.size() == 3 && MOVE_COMMAND.equals(commandSegments.get(COMMAND_INDEX));
    }

    private MovePieceRequest createData(String input) {
        List<String> commandSegments = Arrays.asList(input.split(SEGMENT_SPLIT_DELIMITER));
        Coordinate source = createCoordinate(commandSegments.get(SOURCE_INDEX));
        Coordinate target = createCoordinate(commandSegments.get(TARGET_INDEX));

        return new MovePieceRequest(source, target);
    }

    private Coordinate createCoordinate(String input) {
        List<String> coordinateSegments = Arrays.asList(input.split(""));
        validateCoordinateSegmentSize(coordinateSegments);
        int rankValue = Integer.parseInt(coordinateSegments.get(RANK_INDEX));
        char fileValue = coordinateSegments.get(FILE_INDEX).charAt(0);

        return new Coordinate(rankValue, fileValue);
    }

    private void validateCoordinateSegmentSize(List<String> coordinateSegments) {
        if (coordinateSegments.size() != 2) {
            throw new IllegalArgumentException("올바른 형식의 좌표를 입력해주세요. ex) a2");
        }
    }

    public static boolean canCreate(String input) {
        return input.startsWith(MOVE_COMMAND);
    }

    @Override
    public boolean isMove() {
        return true;
    }

    @Override
    public boolean isStatus() {
        return false;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public MovePieceRequest getData() {
        return data;
    }
}
