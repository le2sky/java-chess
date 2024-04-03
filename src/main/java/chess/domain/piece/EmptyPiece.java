package chess.domain.piece;

class EmptyPiece extends AbstractPiece {

    private static final Piece INSTANCE = new EmptyPiece();

    private EmptyPiece() {
        super(PieceType.EMPTY, Team.EMPTY);
    }

    public static Piece getInstance() {
        return INSTANCE;
    }

    @Override
    void validatePieceMoveRule(Coordinate source, Coordinate target, Pieces pieces) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Score calculateScore(Coordinate source, Pieces pieces) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
