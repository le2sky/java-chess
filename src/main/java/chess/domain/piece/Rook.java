package chess.domain.piece;

import java.util.List;

public class Rook extends AbstractOnlyUsingPathPiece {

    public Rook(Team team) {
        super(PieceType.ROOK, team, List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.LEFT,
                Direction.RIGHT
        ));
    }

    @Override
    public Score calculateScore(Coordinate source, Pieces pieces) {
        return new Score(5);
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
