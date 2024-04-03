package chess.persistence.mysql;

import java.util.List;
import chess.domain.board.Board;
import chess.domain.board.BoardRepository;
import chess.domain.piece.Coordinate;

public class MySqlBoardRepository implements BoardRepository {

    private final BoardHistoryDao boardHistoryDao;

    public MySqlBoardRepository() {
        this(new MySqlBoardHistoryDao());
    }

    public MySqlBoardRepository(BoardHistoryDao boardHistoryDao) {
        this.boardHistoryDao = boardHistoryDao;
    }

    @Override
    public boolean hasContinuableBoard() {
        List<BoardHistoryEntity> histories = boardHistoryDao.findAll();

        return !histories.isEmpty();
    }

    @Override
    public Board loadBoard() {
        try {
            return createBoard();
        } catch (Exception exception) {
            throw new IllegalStateException("보드를 불러오는데, 실패했습니다.");
        }
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
    }
}
