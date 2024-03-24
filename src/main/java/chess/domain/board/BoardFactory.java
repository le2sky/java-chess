package chess.domain.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.Team;

public class BoardFactory {

    private static final int INITIAL_WHITE_PAWN_RANK = 2;
    private static final int INITIAL_BLACK_PAWN_RANK = 7;
    private static final int INITIAL_WHITE_SPECIAL_RANK = 1;
    private static final int INITIAL_BLACK_SPECIAL_RANK = 8;

    private BoardFactory() {
    }

    public static boolean isInitialRank(Coordinate coordinate) {
        int targetRank = coordinate.getRank();
        List<Integer> initialRanks = List.of(
                INITIAL_WHITE_PAWN_RANK,
                INITIAL_BLACK_PAWN_RANK,
                INITIAL_WHITE_SPECIAL_RANK,
                INITIAL_BLACK_SPECIAL_RANK
        );

        return initialRanks.stream()
                .anyMatch(initialRank -> targetRank == initialRank);
    }

    public static Map<Coordinate, Piece> createInitialPieces() {
        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        initializeWhitePiece(pieces);
        initializeBlackPiece(pieces);

        return pieces;
    }

    private static void initializeWhitePiece(HashMap<Coordinate, Piece> pieces) {
        initializeSpecialPiece(INITIAL_WHITE_SPECIAL_RANK, Team.WHITE, pieces);
        initializePawn(INITIAL_WHITE_PAWN_RANK, Team.WHITE, pieces);
    }

    private static void initializeBlackPiece(HashMap<Coordinate, Piece> pieces) {
        initializeSpecialPiece(INITIAL_BLACK_SPECIAL_RANK, Team.BLACK, pieces);
        initializePawn(INITIAL_BLACK_PAWN_RANK, Team.BLACK, pieces);
    }

    private static void initializePawn(
            int rankValue,
            Team team,
            HashMap<Coordinate, Piece> pieces) {
        for (char fileValue = 'a'; fileValue <= 'h'; fileValue++) {
            pieces.put(new Coordinate(rankValue, fileValue), new Pawn(team));
        }
    }

    private static void initializeSpecialPiece(int rankValue, Team team, HashMap<Coordinate, Piece> pieces) {
        pieces.put(new Coordinate(rankValue, 'e'), new King(team));
        pieces.put(new Coordinate(rankValue, 'd'), new Queen(team));
        pieces.put(new Coordinate(rankValue, 'c'), new Bishop(team));
        pieces.put(new Coordinate(rankValue, 'f'), new Bishop(team));
        pieces.put(new Coordinate(rankValue, 'b'), new Knight(team));
        pieces.put(new Coordinate(rankValue, 'g'), new Knight(team));
        pieces.put(new Coordinate(rankValue, 'a'), new Rook(team));
        pieces.put(new Coordinate(rankValue, 'h'), new Rook(team));
    }
}
