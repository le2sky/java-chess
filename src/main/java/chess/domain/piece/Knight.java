package chess.domain.piece;

import java.util.List;

public class Knight extends AbstractNonPathPiece {

    public Knight(Team team) {
        super(PieceType.KNIGHT, team, List.of(
                Direction.UP_UP_LEFT,
                Direction.UP_UP_RIGHT,
                Direction.DOWN_DOWN_LEFT,
                Direction.DOWN_DOWN_RIGHT,
                Direction.LEFT_LEFT_UP,
                Direction.LEFT_LEFT_DOWN,
                Direction.RIGHT_RIGHT_UP,
                Direction.RIGHT_RIGHT_DOWN
        ));
    }

    @Override
    public Score calculateScore(Coordinate source, Pieces pieces) {
        return new Score(2.5);
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
