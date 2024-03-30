package chess.domain.piece;

import java.util.ArrayList;
import java.util.List;
import chess.domain.piece.exception.InvalidMoveException;
import chess.domain.piece.exception.ObstacleException;

abstract class AbstractSlidingPiece extends AbstractPiece {

    private final List<Direction> directions;

    public AbstractSlidingPiece(PieceType type, Team team, List<Direction> directions) {
        super(type, team);

        this.directions = directions;
    }

    @Override
    void validatePieceMoveRule(Coordinate source, Coordinate target, Pieces pieces) {
        List<Coordinate> path = createTargetIncludedPath(source, target);

        validateObstacle(target, path, pieces);
    }

    private List<Coordinate> createTargetIncludedPath(Coordinate source, Coordinate target) {
        return directions.stream()
                .map(direction -> createPath(source, direction))
                .filter(coordinates -> coordinates.contains(target))
                .findFirst()
                .orElseThrow(InvalidMoveException::new);
    }

    private List<Coordinate> createPath(Coordinate start, Direction direction) {
        List<Coordinate> slidingPath = new ArrayList<>();
        Weight weight = direction.getWeight();
        Coordinate nowCoordinate = start;

        while (nowCoordinate.canPlus(weight)) {
            nowCoordinate = nowCoordinate.plus(weight);
            slidingPath.add(nowCoordinate);
        }

        return slidingPath;
    }

    private void validateObstacle(Coordinate target, List<Coordinate> path, Pieces pieces) {
        boolean hasNotObstacle = path.stream()
                .map(pieces::isPiecePresent)
                .limit(path.indexOf(target))
                .noneMatch(hasPiece -> hasPiece);

        if (!hasNotObstacle) {
            throw new ObstacleException();
        }
    }
}
