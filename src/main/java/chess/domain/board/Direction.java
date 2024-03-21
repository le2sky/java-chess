package chess.domain.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum Direction {

    LEFT(Map.entry(-1, 0)),
    RIGHT(Map.entry(1, 0)),
    UP(Map.entry(0, 1)),
    DOWN(Map.entry(0, -1)),
    RIGHT_UP(Map.entry(1, 1)),
    RIGHT_DOWN(Map.entry(1, -1)),
    LEFT_UP(Map.entry(-1, 1)),
    LEFT_DOWN(Map.entry(-1, -1));

    private final Map.Entry<Integer, Integer> entry;

    Direction(Map.Entry<Integer, Integer> entry) {
        this.entry = entry;
    }

    public static Direction of(int xDiff, int yDiff) {
        return Arrays.stream(values())
                .filter((it) -> it.entry.equals(Map.entry((Integer.compare(xDiff, 0)), Integer.compare(yDiff, 0))))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("같은 곳으로는 이동할 수 없습니다."));
    }

    public List<Coordinate> createPath(Coordinate start) {
        List<Coordinate> coordinates = new ArrayList<>();
        int fileWeight = entry.getKey();
        int rankWeight = entry.getValue();
        int nextRank = start.getRank() + rankWeight;
        char nextFile = (char) (start.getFile() + fileWeight);

        while (nextRank >= 1 && nextRank <= 8 && nextFile >= 'a' && nextFile <= 'h') {
            coordinates.add(new Coordinate(nextRank, nextFile));
            nextRank += rankWeight;
            nextFile += fileWeight;
        }

        return coordinates;
    }
}