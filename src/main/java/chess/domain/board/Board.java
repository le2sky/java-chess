package chess.domain.board;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;

public class Board {

    private static final int INITIAL_WHITE_SPECIAL_RANK = 1;
    private static final int INITIAL_WHITE_PAWN_RANK = 2;
    private static final int INITIAL_BLACK_SPECIAL_RANK = 8;
    private static final int INITIAL_BLACK_PAWN_RANK = 7;
    private static final Piece EMPTY_PIECE = new Empty();

    private final Map<Coordinate, Piece> pieces = new HashMap<>();

    public Board(Map<Coordinate, Piece> pieces) {
        this.pieces.putAll(pieces);
    }

    public Board() {
        this(BoardFactory.createInitialPieces(
                INITIAL_WHITE_SPECIAL_RANK,
                INITIAL_WHITE_PAWN_RANK,
                INITIAL_BLACK_SPECIAL_RANK,
                INITIAL_BLACK_PAWN_RANK
        ));
    }

    public Piece findByCoordinate(Coordinate coordinate) {
        return pieces.getOrDefault(coordinate, EMPTY_PIECE);
    }

    public boolean isPiecePresent(Coordinate coordinate) {
        return pieces.containsKey(coordinate);
    }

    public void move(Coordinate source, Coordinate target) {
        validateSourceExist(source);
        validateMovable(source, target);
        updateBoard(source, target);
    }

    private void validateSourceExist(Coordinate source) {
        if (!pieces.containsKey(source)) {
            throw new NoSuchElementException("보드에 움직일 대상이 없습니다.");
        }
    }

    private void validateMovable(Coordinate source, Coordinate target) {
        Piece sourcePiece = pieces.get(source);
        sourcePiece.validateMovable(source, target, this);
    }

    private void updateBoard(Coordinate source, Coordinate target) {
        Piece sourcePiece = pieces.get(source);
        pieces.remove(source);
        pieces.put(target, sourcePiece);
    }
}
