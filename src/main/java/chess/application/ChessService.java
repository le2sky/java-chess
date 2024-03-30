package chess.application;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import chess.application.request.MovePieceRequest;
import chess.domain.board.Board;
import chess.domain.board.BoardRepository;
import chess.domain.board.ChessResult;
import chess.domain.board.Coordinate;
import chess.domain.board.Pieces;
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

    public Map<Team, Double> findChessScore() {
        Board board = boardRepository.loadBoard();

        return Arrays.stream(Team.values())
                .filter(team -> team != Team.EMPTY)
                .collect(Collectors.toMap(team -> team, board::nowScore));
    }

    public void clearPreviousBoard() {
        boardRepository.clear();
    }
}
