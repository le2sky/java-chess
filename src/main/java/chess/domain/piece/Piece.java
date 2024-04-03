package chess.domain.piece;

public interface Piece {

    void validateMovable(Coordinate source, Coordinate target, Pieces pieces);

    Score calculateScore(Coordinate source, Pieces pieces);

    boolean isSameTeam(Team team);

    boolean isEnemy(Piece other);

    boolean isKing();

    PieceType getType();

    Team getTeam();
}
