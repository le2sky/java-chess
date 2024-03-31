package chess.domain.piece;

import java.util.List;
import chess.domain.piece.exception.InvalidMoveException;
import chess.domain.piece.exception.ObstacleException;

abstract class AbstractOnlyUsingPathPiece extends AbstractPiece {

    private final List<Direction> directions;

    public AbstractOnlyUsingPathPiece(PieceType type, Team team, List<Direction> directions) {
        super(type, team);

        this.directions = directions;
    }

    @Override
    void validatePieceMoveRule(Coordinate source, Coordinate target, Pieces pieces) {
        Path path = createPath(source, target);
        if (path.hasObstacle(target, pieces)) {
            throw new ObstacleException();
        }
    }

    private Path createPath(Coordinate source, Coordinate target) {
        return directions.stream()
                .map(direction -> Path.createPath(direction, source))
                .filter(path -> path.contains(target))
                .findFirst()
                .orElseThrow(InvalidMoveException::new);
    }
}
