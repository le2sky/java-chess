package chess.application;

import java.util.Map;
import chess.application.request.MovePieceRequest;
import chess.domain.board.Board;
import chess.domain.board.BoardRepository;
import chess.domain.board.ChessResult;
import chess.domain.piece.Coordinate;
import chess.domain.piece.Pieces;
import chess.domain.piece.Score;
import chess.domain.piece.Team;

public class ChessService {

    private final BoardRepository boardRepository;

    public ChessService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public boolean hasContinuableBoard() {
        return boardRepository.hasContinuableBoard();
    }

    public void move(MovePieceRequest request) {
        Board board = boardRepository.loadBoard();
        Coordinate source = request.source();
        Coordinate target = request.target();

        board.move(source, target);

        boardRepository.saveMoveHistory(source, target);
    }

    public boolean isChessPlaying() {
        Board board = boardRepository.loadBoard();
        return board.isPlaying();
    }

    public Pieces findPieces() {
        Board board = boardRepository.loadBoard();
        return board.getPieces();
    }

    public ChessResult findChessResult() {
        Board board = boardRepository.loadBoard();
        return board.showResult();
    }

    public Map<Team, Score> findChessScore() {
        Board board = boardRepository.loadBoard();
        return board.showScore();
    }

    public void clearPreviousBoard() {
        boardRepository.clear();
    }
}
