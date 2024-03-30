package chess.domain.piece;

import java.util.Map;

public class Pieces {

    private final Map<Coordinate, Piece> pieces;

    public Pieces(Map<Coordinate, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece findByCoordinate(Coordinate coordinate) {
        return pieces.getOrDefault(coordinate, EmptyPiece.getInstance());
    }

    public boolean isPiecePresent(Coordinate coordinate) {
        return pieces.containsKey(coordinate);
    }

    public void move(Coordinate source, Coordinate target) {
        Piece sourcePiece = pieces.get(source);
        pieces.remove(source);
        pieces.put(target, sourcePiece);
    }

    public double calculateTotalScore(Team targetTeam) {
        return pieces.keySet().stream()
                .mapToDouble(coordinate -> calculateEachScore(coordinate, targetTeam))
                .sum();
    }

    private double calculateEachScore(Coordinate coordinate, Team targetTeam) {
        Piece piece = findByCoordinate(coordinate);
        if (!piece.isSameTeam(targetTeam)) {
            return 0;
        }

        return piece.calculateScore(coordinate, this);
    }
}
