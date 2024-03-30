package chess.domain.board;

import java.util.Map;
import chess.domain.piece.Coordinate;
import chess.domain.piece.Piece;
import chess.domain.piece.Pieces;
import chess.domain.piece.PiecesFactory;
import chess.domain.piece.Team;

public class Board {

    private BoardState state;

    public Board(Map<Coordinate, Piece> pieces) {
        this(new Pieces(pieces));
    }

    public Board() {
        this(PiecesFactory.createInitialPieces());
    }

    public Board(Pieces pieces) {
        this(new PlayingState(pieces, new Turn(Team.WHITE)));
    }

    public Board(BoardState state) {
        this.state = state;
    }

    public void move(Coordinate source, Coordinate target) {
        state = state.move(source, target);
    }

    public double nowScore(Team team) {
        return state.nowScore(team);
    }

    public ChessResult showResult() {
        return state.showResult();
    }

    public boolean isPlaying() {
        return state.isPlaying();
    }

    public Pieces getPieces() {
        return state.getPieces();
    }
}
