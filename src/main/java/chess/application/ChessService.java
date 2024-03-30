package chess.application;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import chess.application.request.MovePieceRequest;
import chess.domain.board.Board;
import chess.domain.board.ChessResult;
import chess.domain.board.Coordinate;
import chess.domain.board.Pieces;
import chess.domain.piece.Team;

public class ChessService {

    private final Board board = new Board();

    public void move(MovePieceRequest request) {
        Coordinate source = request.source();
        Coordinate target = request.target();
        board.move(source, target);
    }

    public boolean isChessPlaying() {
        return board.isPlaying();
    }

    public Pieces findPieces() {
        return board.getPieces();
    }

    public ChessResult findChessResult() {
        return board.showResult();
    }

    public Map<Team, Double> findChessScore() {
        return Arrays.stream(Team.values())
                .filter(team -> team != Team.EMPTY)
                .collect(Collectors.toMap(team -> team, board::nowScore));
    }
}
