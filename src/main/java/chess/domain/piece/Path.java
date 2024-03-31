package chess.domain.piece;

import java.util.ArrayList;
import java.util.List;

class Path {

    private final List<Coordinate> coordinates;

    public Path(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public static Path createPath(Direction direction, Coordinate start) {
        List<Coordinate> coordinates = new ArrayList<>();
        Weight weight = direction.getWeight();
        Coordinate nowCoordinate = start;

        while (nowCoordinate.canPlus(weight)) {
            nowCoordinate = nowCoordinate.plus(weight);
            coordinates.add(nowCoordinate);
        }

        return new Path(coordinates);
    }

    public boolean contains(Coordinate coordinate) {
        return coordinates.contains(coordinate);
    }

    public boolean hasObstacle(Coordinate target, Pieces pieces) {
        return coordinates.stream()
                .map(pieces::isPiecePresent)
                .limit(coordinates.indexOf(target))
                .anyMatch(hasPiece -> hasPiece);
    }

    public boolean hasSamePiece(Piece piece, Pieces pieces) {
        return coordinates.stream()
                .map(pieces::findByCoordinate)
                .anyMatch(piece::equals);
    }
}
