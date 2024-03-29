package chess.domain.piece;

import java.util.List;
import chess.domain.board.Coordinate;
import chess.domain.board.Pieces;

public class King extends AbstractNonSlidingPiece {

    public King(Team team) {
        super(PieceType.KING, team, List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.LEFT,
                Direction.RIGHT,
                Direction.UP_LEFT,
                Direction.DOWN_LEFT,
                Direction.UP_RIGHT,
                Direction.DOWN_RIGHT
        ));
    }

    @Override
    public double calculateScore(Coordinate source, Pieces pieces) {
        return 0;
    }
}
