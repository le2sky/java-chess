package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        assertThatCode(Board::new)
                .doesNotThrowAnyException();
    }

    @DisplayName("백팀의 킹은 d1에 있다.")
    @Test
    void whiteKing() {
        Board board = new Board();

        Piece result = board.findByCoordinate(new Coordinate(1, 'd'));

        Piece expected = new Piece(PieceType.KING, Team.WHITE);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("백팀의 퀸은 e1 있다.")
    @Test
    void whiteQueen() {
        Board board = new Board();

        Piece result = board.findByCoordinate(new Coordinate(1, 'e'));

        Piece expected = new Piece(PieceType.QUEEN, Team.WHITE);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("백팀의 비숍은 c1, f1에 있다.")
    @Test
    void whiteBishop() {
        Board board = new Board();

        Piece bishop1 = board.findByCoordinate(new Coordinate(1, 'c'));
        Piece bishop2 = board.findByCoordinate(new Coordinate(1, 'f'));

        Piece expected = new Piece(PieceType.BISHOP, Team.WHITE);

        assertThat(bishop1)
                .isEqualTo(bishop2)
                .isEqualTo(expected);
    }

    @DisplayName("백팀의 나이트는 b1, g1에 있다.")
    @Test
    void whiteKnight() {
        Board board = new Board();

        Piece knight1 = board.findByCoordinate(new Coordinate(1, 'b'));
        Piece knight2 = board.findByCoordinate(new Coordinate(1, 'g'));

        Piece expected = new Piece(PieceType.KNIGHT, Team.WHITE);

        assertThat(knight1)
                .isEqualTo(knight2)
                .isEqualTo(expected);
    }

    @DisplayName("백팀의 룩은 a1, h1에 있다.")
    @Test
    void whiteRook() {
        Board board = new Board();

        Piece rook1 = board.findByCoordinate(new Coordinate(1, 'a'));
        Piece rook2 = board.findByCoordinate(new Coordinate(1, 'h'));

        Piece expected = new Piece(PieceType.ROOK, Team.WHITE);

        assertThat(rook1)
                .isEqualTo(rook2)
                .isEqualTo(expected);
    }

    @DisplayName("백팀의 폰은 랭크가 2이고 파일은 A부터 H까지다.")
    @Test
    void whitePawn() {
        Board board = new Board();

        List<Piece> result = new ArrayList<>();
        for (char i = 'a'; i <= 'h'; i++) {
            Piece piece = board.findByCoordinate(new Coordinate(2, i));
            result.add(piece);
        }

        assertThat(result).isEqualTo(Collections.nCopies(8, new Piece(PieceType.PAWN, Team.WHITE)));
    }

    @DisplayName("흑팀의 킹은 d8에 있다.")
    @Test
    void blackKing() {
        Board board = new Board();

        Piece result = board.findByCoordinate(new Coordinate(8, 'd'));

        Piece expected = new Piece(PieceType.KING, Team.BLACK);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("흑팀의 퀸은 e8 있다.")
    @Test
    void blackQueen() {
        Board board = new Board();

        Piece result = board.findByCoordinate(new Coordinate(8, 'e'));

        Piece expected = new Piece(PieceType.QUEEN, Team.BLACK);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("흑팀의 비숍은 c8, f8에 있다.")
    @Test
    void blackBishop() {
        Board board = new Board();

        Piece bishop1 = board.findByCoordinate(new Coordinate(8, 'c'));
        Piece bishop2 = board.findByCoordinate(new Coordinate(8, 'f'));

        Piece expected = new Piece(PieceType.BISHOP, Team.BLACK);

        assertThat(bishop1)
                .isEqualTo(bishop2)
                .isEqualTo(expected);
    }

    @DisplayName("흑팀의 나이트는 b8, g8에 있다.")
    @Test
    void blackKnight() {
        Board board = new Board();

        Piece knight1 = board.findByCoordinate(new Coordinate(8, 'b'));
        Piece knight2 = board.findByCoordinate(new Coordinate(8, 'g'));

        Piece expected = new Piece(PieceType.KNIGHT, Team.BLACK);

        assertThat(knight1)
                .isEqualTo(knight2)
                .isEqualTo(expected);
    }

    @DisplayName("흑팀의 룩은 a8, h8에 있다.")
    @Test
    void blackRook() {
        Board board = new Board();

        Piece rook1 = board.findByCoordinate(new Coordinate(8, 'a'));
        Piece rook2 = board.findByCoordinate(new Coordinate(8, 'h'));

        Piece expected = new Piece(PieceType.ROOK, Team.BLACK);

        assertThat(rook1)
                .isEqualTo(rook2)
                .isEqualTo(expected);
    }

    @DisplayName("흑팀의 폰은 랭크가 7이고 파일은 A부터 H까지다.")
    @Test
    void blackPawn() {
        Board board = new Board();

        List<Piece> result = new ArrayList<>();
        for (char i = 'a'; i <= 'h'; i++) {
            Piece piece = board.findByCoordinate(new Coordinate(7, i));
            result.add(piece);
        }

        assertThat(result).isEqualTo(Collections.nCopies(8, new Piece(PieceType.PAWN, Team.BLACK)));
    }

    @DisplayName("보드의 기물의 수는 32개이다.")
    @Test
    void boardPieceSize() {
        Board board = new Board();

        int size = board.pieceSize();

        assertThat(size).isEqualTo(32);
    }
}