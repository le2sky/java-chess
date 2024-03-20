package chess.domain.piece;

import java.util.ArrayList;
import java.util.List;
import chess.domain.Coordinate;

public class Rook extends AbstractPiece {

    public Rook(Team team) {
        super(PieceType.ROOK, team);
    }

    @Override
    public List<Coordinate> findAllPossibleCoordinate(Coordinate start) {
        List<Coordinate> possibleCoordinate = new ArrayList<>();
        for (int rankValue = 1; rankValue <= 8; rankValue++) {
            possibleCoordinate.addAll(createOneLinePossibleCoordinate(start, rankValue));
        }

        return possibleCoordinate;
    }

    private List<Coordinate> createOneLinePossibleCoordinate(Coordinate start, int rankValue) {
        List<Coordinate> rowCoordinate = new ArrayList<>();
        for (char fileValue = 'a'; fileValue <= 'h'; fileValue++) {
            if (rankValue == start.getRank() && fileValue == start.getFile()) {
                continue;
            }

            if (rankValue == start.getRank() || fileValue == start.getFile()) {
                rowCoordinate.add(new Coordinate(rankValue, fileValue));
            }
        }

        return rowCoordinate;
    }
}