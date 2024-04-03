package chess.domain.board;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import chess.domain.piece.Coordinate;
import chess.domain.piece.Piece;
import chess.domain.piece.Pieces;
import chess.domain.piece.Score;
import chess.domain.piece.Team;

class PlayingBoard implements MutableBoard {

    private final Pieces pieces;
    private final Turn turn;

    public PlayingBoard(Pieces pieces, Turn turn) {
        this.pieces = pieces;
        this.turn = turn;
    }

    @Override
    public MutableBoard move(Coordinate source, Coordinate target) {
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

    private MutableBoard updateBoard(Coordinate source, Coordinate target) {
        Piece targetPiece = pieces.findByCoordinate(target);
        pieces.move(source, target);
        if (targetPiece.isKing()) {
            return new EndBoard(turn, pieces);
        }
        turn.change();
        return this;
    }

    @Override
    public Map<Team, Score> showScore() {
        return Arrays.stream(Team.values())
                .filter(team -> !team.isEmpty())
                .collect(Collectors.toMap(team -> team, pieces::calculateTotalScore));
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
