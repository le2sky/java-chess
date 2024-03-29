package chess.domain.board;

import java.util.Map;
import java.util.NoSuchElementException;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;

// TODO: State 패턴으로 개선해볼 수 있을것
public class Board {

    private final Pieces pieces;
    private final Turn turn;
    private Team winner; //TODO: 게임이 종료되었을 때, 마지막으로 공격한 턴이 승자이다.
    private BoardState state;

    // TODO: 생성자 복잡도 개선
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

    // TODO: instanceof King을 어떻게할 것인지 고민해볼 것. getTeam()을 개선해볼 것.
    private void attack(Coordinate source, Coordinate target) {
        Piece targetPiece = pieces.findByCoordinate(target);
        if (targetPiece instanceof King) {
            state = BoardState.END;
            winner = targetPiece.getTeam().opposite();
        }

        pieces.move(source, target);
    }

    // TODO: 메서드 순서 개선
    public double nowScore(Team team) {
        validateChessEnd();

        return pieces.calculateTotalScore(team);
    }

    public ChessResult showResult() {
        if (isPlaying()) {
            throw new IllegalStateException("아직 진행중인 체스입니다.");
        }

        return new ChessResult(winner, winner.opposite());
    }

    public boolean isPlaying() {
        return state.isPlaying();
    }

    public Pieces getPieces() {
        return pieces;
    }
}
