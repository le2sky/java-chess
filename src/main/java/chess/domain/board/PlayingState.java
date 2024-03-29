package chess.domain.board;

import java.util.NoSuchElementException;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;

class PlayingState implements BoardState {

    private final Pieces pieces;
    private final Turn turn;

    public PlayingState(Pieces pieces, Turn turn) {
        this.pieces = pieces;
        this.turn = turn;
    }

    @Override
    public BoardState move(Coordinate source, Coordinate target) {
        validateSourceExist(source);
        validateTurn(source);
        validateMovable(source, target);

        return updateBoard(source, target);
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

    // TODO: instanceof에 대해서 고민해보기
    private BoardState updateBoard(Coordinate source, Coordinate target) {
        Piece targetPiece = pieces.findByCoordinate(target);
        pieces.move(source, target);
        if (!(targetPiece instanceof King)) {
            turn.change();
            return this;
        }

        return new EndState(turn, pieces);
    }

    @Override
    public double nowScore(Team team) {
        return pieces.calculateTotalScore(team);
    }

    @Override
    public ChessResult showResult() {
        throw new IllegalStateException("아직 진행중인 체스입니다.");
    }

    @Override
    public boolean isPlaying() {
        return true;
    }

    @Override
    public Pieces getPieces() {
        return pieces;
    }
}
