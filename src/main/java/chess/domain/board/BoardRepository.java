package chess.domain.board;

import chess.domain.piece.Coordinate;

public interface BoardRepository {

    boolean hasContinuableBoard();

    Board loadBoard();

    void saveMoveHistory(Coordinate source, Coordinate target);

    void clear();
}
