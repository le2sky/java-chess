package chess.domain.piece;

import java.util.List;
import chess.domain.board.Coordinate;
import chess.domain.board.Pieces;

public class Bishop extends AbstractSlidingPiece {

    public Bishop(Team team) {
        super(PieceType.BISHOP, team, List.of(
                Direction.DOWN_LEFT,
                Direction.UP_LEFT,
                Direction.DOWN_RIGHT,
                Direction.UP_RIGHT
        ));
    }

    @Override
    public double calculateScore(Coordinate source, Pieces pieces) {
        return 3;
    }
}
