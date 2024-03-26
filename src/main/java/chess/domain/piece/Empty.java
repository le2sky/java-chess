package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.board.Coordinate;

public class Empty extends AbstractPiece {

    private static final Piece INSTANCE = new Empty();

    private Empty() {
        super(PieceType.EMPTY, Team.EMPTY);
    }

    public static Piece getInstance() {
        return INSTANCE;
    }

    @Override
    void validatePieceMoveRule(Coordinate source, Coordinate target, Board board) {
        throw new UnsupportedOperationException();
    }
}
