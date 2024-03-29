package chess.domain.board;

import chess.domain.piece.Team;

class EndState implements BoardState {

    private static final String ALREADY_END_MESSAGE = "이미 종료된 체스입니다.";

    private final Turn turn;
    private final Pieces pieces;

    public EndState(Turn turn, Pieces pieces) {
        this.turn = turn;
        this.pieces = pieces;
    }

    @Override
    public BoardState move(Coordinate source, Coordinate target) {
        throw new IllegalStateException(ALREADY_END_MESSAGE);
    }

    @Override
    public double nowScore(Team team) {
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
