package chess.domain.board;

import java.util.Map;
import chess.domain.piece.Coordinate;
import chess.domain.piece.Piece;
import chess.domain.piece.Pieces;
import chess.domain.piece.PiecesFactory;
import chess.domain.piece.Score;
import chess.domain.piece.Team;

public class Board {

    private MutableBoard board;

    public Board(Map<Coordinate, Piece> pieces) {
        this(new Pieces(pieces));
    }

    public Board() {
        this(PiecesFactory.createInitialPieces());
    }

    public Board(Pieces pieces) {
        this(new PlayingBoard(pieces, new Turn(Team.WHITE)));
    }

    public Board(MutableBoard board) {
        this.board = board;
    }

    public void move(Coordinate source, Coordinate target) {
        board = board.move(source, target);
    }

    public Map<Team, Score> showScore() {
        return board.showScore();
    }

    public ChessResult showResult() {
        return board.showResult();
    }

    public boolean isPlaying() {
        return board.isPlaying();
    }

    public Pieces getPieces() {
        return board.getPieces();
    }
}
