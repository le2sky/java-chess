package chess.domain;

import java.util.HashMap;
import java.util.Map;

class Board {

    private static final int INITIAL_WHITE_SPECIAL_RANK = 1;
    private static final int INITIAL_WHITE_PAWN_RANK = 2;
    private static final int INITIAL_BLACK_SPECIAL_RANK = 8;
    private static final int INITIAL_BLACK_PAWN_RANK = 7;

    private final Map<Coordinate, Piece> board = new HashMap<>();

    public Board() {
        initializeWhitePiece();
        initializeBlackPiece();
    }

    private void initializeWhitePiece() {
        initializeSpecialPiece(INITIAL_WHITE_SPECIAL_RANK, Team.WHITE);
        initializePawn(INITIAL_WHITE_PAWN_RANK, Team.WHITE);
    }

    private void initializeBlackPiece() {
        initializeSpecialPiece(INITIAL_BLACK_SPECIAL_RANK, Team.BLACK);
        initializePawn(INITIAL_BLACK_PAWN_RANK, Team.BLACK);
    }

    private void initializePawn(int rankValue, Team team) {
        for (char fileValue = 'a'; fileValue <= 'h'; fileValue++) {
            board.put(new Coordinate(rankValue, fileValue), new Piece(PieceType.PAWN, team));
        }
    }

    private void initializeSpecialPiece(int rankValue, Team team) {
        board.put(new Coordinate(rankValue, 'd'), new Piece(PieceType.KING, team));
        board.put(new Coordinate(rankValue, 'e'), new Piece(PieceType.QUEEN, team));
        board.put(new Coordinate(rankValue, 'c'), new Piece(PieceType.BISHOP, team));
        board.put(new Coordinate(rankValue, 'f'), new Piece(PieceType.BISHOP, team));
        board.put(new Coordinate(rankValue, 'b'), new Piece(PieceType.KNIGHT, team));
        board.put(new Coordinate(rankValue, 'g'), new Piece(PieceType.KNIGHT, team));
        board.put(new Coordinate(rankValue, 'a'), new Piece(PieceType.ROOK, team));
        board.put(new Coordinate(rankValue, 'h'), new Piece(PieceType.ROOK, team));
    }

    public Piece findByCoordinate(Coordinate coordinate) {
        return board.get(coordinate);
    }

    public int pieceSize() {
        return board.size();
    }
}
