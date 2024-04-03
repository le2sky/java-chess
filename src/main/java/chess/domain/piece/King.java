package chess.domain.piece;

import java.util.List;

public class King extends AbstractNonPathPiece {

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
    public Score calculateScore(Coordinate source, Pieces pieces) {
        return new Score(0);
    }

    @Override
    public boolean isKing() {
        return true;
    }
}
