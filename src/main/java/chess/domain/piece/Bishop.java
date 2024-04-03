package chess.domain.piece;

import java.util.List;

public class Bishop extends AbstractOnlyUsingPathPiece {

    public Bishop(Team team) {
        super(PieceType.BISHOP, team, List.of(
                Direction.DOWN_LEFT,
                Direction.UP_LEFT,
                Direction.DOWN_RIGHT,
                Direction.UP_RIGHT
        ));
    }

    @Override
    public Score calculateScore(Coordinate source, Pieces pieces) {
        return new Score(3);
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
