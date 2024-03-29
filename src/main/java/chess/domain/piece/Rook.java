package chess.domain.piece;

import java.util.List;
import chess.domain.board.Coordinate;
import chess.domain.board.Pieces;

public class Rook extends AbstractSlidingPiece {

    public Rook(Team team) {
        super(PieceType.ROOK, team, List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.LEFT,
                Direction.RIGHT
        ));
    }

    @Override
    public double calculateScore(Coordinate source, Pieces pieces) {
        return 5;
    }
}
