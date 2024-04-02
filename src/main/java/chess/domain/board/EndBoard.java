package chess.domain.board;

import java.util.Map;
import chess.domain.piece.Coordinate;
import chess.domain.piece.Pieces;
import chess.domain.piece.Score;
import chess.domain.piece.Team;

class EndBoard implements MutableBoard {

    private static final String ALREADY_END_MESSAGE = "이미 종료된 체스입니다.";

    private final Turn turn;
    private final Pieces pieces;

    public EndBoard(Turn turn, Pieces pieces) {
        this.turn = turn;
        this.pieces = pieces;
    }

    @Override
    public MutableBoard move(Coordinate source, Coordinate target) {
        throw new IllegalStateException(ALREADY_END_MESSAGE);
    }

    @Override
    public Map<Team, Score> showScore() {
        throw new IllegalStateException(ALREADY_END_MESSAGE);
    }

    @Override
    public ChessResult showResult() {
        Team nowTeam = turn.getNowTeam();
        return new ChessResult(nowTeam, nowTeam.opposite());
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public Pieces getPieces() {
        return pieces;
    }
}
