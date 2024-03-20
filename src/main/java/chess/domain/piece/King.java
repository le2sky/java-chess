package chess.domain.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import chess.domain.Coordinate;

public class King extends AbstractPiece {

    public static final List<Map.Entry<Integer, Integer>> WEIGHTS = List.of(
            Map.entry(-1,1),
            Map.entry(-1,0),
            Map.entry(-1,-1),
            Map.entry(1,1),
            Map.entry(1,0),
            Map.entry(1,-1),
            Map.entry(0,1),
            Map.entry(0,-1)
    );

    public King(Team team) {
        super(PieceType.KING, team);
    }

    @Override
    public List<Coordinate> findAllPossibleCoordinate(Coordinate start) {
        int startRank = start.getRank();
        char startFile = start.getFile();

        List<Coordinate> possibleCoordinate = new ArrayList<>();

        for (Map.Entry<Integer, Integer> weight : WEIGHTS) {
            int nextRank = startRank + weight.getKey();
            char nextFile = (char) (startFile + weight.getValue());

            try {
                Coordinate coordinate = new Coordinate(nextRank, nextFile);
                possibleCoordinate.add(coordinate);
            } catch (IllegalArgumentException ignored) {
            }
        }

        return possibleCoordinate;
    }
}