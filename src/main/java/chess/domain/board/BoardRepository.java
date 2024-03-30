package chess.domain.board;

public interface BoardRepository {

    boolean hasContinuableBoard();

    Board loadBoard();

    void saveMoveHistory(Coordinate source, Coordinate target);
}
