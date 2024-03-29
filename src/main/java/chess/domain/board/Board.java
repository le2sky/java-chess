package chess.domain.board;

import java.util.Map;
import java.util.NoSuchElementException;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;

public class Board {

    private final Pieces pieces;
    private final Turn turn;
    private BoardState state;

    public Board(Map<Coordinate, Piece> pieces) {
        this(new Pieces(pieces));
    }

    public Board() {
        this(PiecesFactory.createInitialPieces());
    }

    public Board(Pieces pieces) {
        this(pieces, new Turn(Team.WHITE), BoardState.PLAYING);
    }

    public Board(Pieces pieces, Turn turn, BoardState state) {
        this.pieces = pieces;
        this.turn = turn;
        this.state = state;
    }

    public void move(Coordinate source, Coordinate target) {
        validateChessEnd();
        validateSourceExist(source);
        validateTurn(source);
        validateMovable(source, target);
        updateBoard(source, target);
    }

    private void validateChessEnd() {
        if (!isPlaying()) {
            throw new IllegalStateException("이미 종료된 체스입니다.");
        }
    }

    private void validateSourceExist(Coordinate source) {
        if (!pieces.isPiecePresent(source)) {
            throw new NoSuchElementException("보드에 움직일 대상이 없습니다.");
        }
    }

    private void validateTurn(Coordinate source) {
        Piece sourcePiece = pieces.findByCoordinate(source);
        if (!turn.isSameTeam(sourcePiece)) {
            throw new IllegalStateException("상대방이 기물을 둘 차례입니다.");
        }
    }

    private void validateMovable(Coordinate source, Coordinate target) {
        Piece sourcePiece = pieces.findByCoordinate(source);
        sourcePiece.validateMovable(source, target, pieces);
    }

    private void updateBoard(Coordinate source, Coordinate target) {
        attack(source, target);
        turn.change();
    }

    private void attack(Coordinate source, Coordinate target) {
        Piece targetPiece = pieces.findByCoordinate(target);
        if (targetPiece instanceof King) {
            state = BoardState.END;
        }

        pieces.move(source, target);
    }

    public boolean isPlaying() {
        return state.isPlaying();
    }

    public Pieces getPieces() {
        return pieces;
    }
}
