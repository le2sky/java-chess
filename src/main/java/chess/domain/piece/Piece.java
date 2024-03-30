package chess.domain.piece;

public interface Piece {

    void validateMovable(Coordinate source, Coordinate target, Pieces pieces);

    double calculateScore(Coordinate source, Pieces pieces);

    boolean isSameTeam(Team team);

    boolean isEnemy(Piece other);

    PieceType getType();

    Team getTeam();
}
