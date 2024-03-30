package chess.persistence.memory;

import chess.domain.board.Board;
import chess.domain.board.BoardRepository;
import chess.domain.board.Coordinate;

public class MemoryBoardRepository implements BoardRepository {

    private Board board = new Board();

    @Override
    public boolean hasContinuableBoard() {
        return false;
    }

    @Override
    public Board loadBoard() {
        return board;
    }

    @Override
    public void saveMoveHistory(Coordinate source, Coordinate target) {
    }

    @Override
    public void clear() {
        board = new Board();
    }
}
