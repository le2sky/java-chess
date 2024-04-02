package chess.persistence.mysql;

import java.util.List;
import chess.domain.board.Board;
import chess.domain.board.BoardRepository;
import chess.domain.piece.Coordinate;

public class MySqlBoardRepository implements BoardRepository {

    private final BoardHistoryDao boardHistoryDao;
    private Board cached;

    public MySqlBoardRepository() {
        this.boardHistoryDao = new BoardHistoryDao();
    }

    @Override
    public boolean hasContinuableBoard() {
        List<BoardHistoryEntity> histories = boardHistoryDao.findAll();

        return !histories.isEmpty();
    }

    @Override
    public Board loadBoard() {
        return recover();
    }

    @Override
    public void saveMoveHistory(Coordinate source, Coordinate target) {
        BoardHistoryEntity historyEntity = new BoardHistoryEntity(
                source.getRank(),
                source.getFile(),
                target.getRank(),
                target.getFile());

        boardHistoryDao.saveOne(historyEntity);
    }

    @Override
    public void clear() {
        boardHistoryDao.deleteAll();
        cached = recover();
    }

    private Board recover() {
        if (cached != null) {
            return cached;
        }
        cached = createBoard();

        return cached;
    }

    private Board createBoard() {
        Board board = new Board();
        List<BoardHistoryEntity> histories = boardHistoryDao.findAll();
        for (BoardHistoryEntity history : histories) {
            Coordinate source = new Coordinate(history.sourceRank, history.sourceFile);
            Coordinate target = new Coordinate(history.targetRank, history.targetFile);
            board.move(source, target);
        }
        return board;
    }
}
