package chess.domain.piece;

import java.util.List;
import chess.domain.piece.exception.InvalidMoveException;

abstract class AbstractNonPathPiece extends AbstractPiece {

    private final List<Direction> directions;

    public AbstractNonPathPiece(PieceType type, Team team, List<Direction> directions) {
        super(type, team);

        this.directions = directions;
    }

    @Override
    void validatePieceMoveRule(Coordinate source, Coordinate target, Pieces pieces) {
        List<Coordinate> possibleCoordinates = createPossibleCoordinates(source);
        if (!possibleCoordinates.contains(target)) {
            throw new InvalidMoveException();
        }
    }

    private List<Coordinate> createPossibleCoordinates(Coordinate source) {
        return directions.stream()
                .map(Direction::getWeight)
                .filter(source::canPlus)
                .map(source::plus)
                .toList();
    }
}
