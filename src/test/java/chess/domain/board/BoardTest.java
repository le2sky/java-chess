package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import chess.domain.piece.Coordinate;
import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Pieces;
import chess.domain.piece.PiecesFactory;
import chess.domain.piece.Score;
import chess.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        assertThatCode(Board::new)
                .doesNotThrowAnyException();
    }

    @DisplayName("source 좌표에 기물이 없으면 기물을 움직일 수 없다.")
    @Test
    void noSource() {
        Board emptyBoard = new Board(new HashMap<>());
        Coordinate source = new Coordinate(2, 'a');
        Coordinate target = new Coordinate(3, 'b');

        assertThatThrownBy(() -> emptyBoard.move(source, target))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("기물이 갈 수 있는 곳이라면, 보드를 업데이트한다.")
    @Test
    void move() {
        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        Piece sourcePiece = new Pawn(Team.WHITE);
        Coordinate source = new Coordinate(2, 'a');
        Coordinate target = new Coordinate(4, 'a');
        pieces.put(source, sourcePiece);
        Board board = new Board(pieces);

        assertThatCode(() -> board.move(source, target))
                .doesNotThrowAnyException();
    }

    @DisplayName("현재 턴에 해당하는 진영에 소속된 기물만 움직일 수 있다.")
    @Test
    void validateInvalidTurn() {
        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        Piece sourcePiece = new Pawn(Team.WHITE);
        Coordinate source = new Coordinate(4, 'a');
        Coordinate middle = new Coordinate(5, 'a');
        Coordinate target = new Coordinate(6, 'a');
        pieces.put(source, sourcePiece);
        Board board = new Board(pieces);
        board.move(source, middle);

        assertThatThrownBy(() -> board.move(middle, target))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("상대방이 기물을 둘 차례입니다.");
    }

    @DisplayName("체스가 종료되면, 기물을 움직일 수 없다.")
    @Test
    void validateChessEnd() {
        Pieces pieces = PiecesFactory.createInitialPieces();
        Turn turn = new Turn(Team.WHITE);
        Board endBoard = new Board(new EndBoard(turn, pieces));
        Coordinate source = new Coordinate(2, 'a');
        Coordinate target = new Coordinate(4, 'a');

        assertThatThrownBy(() -> endBoard.move(source, target))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 종료된 체스입니다.");
    }

    @DisplayName("남아있는 기물들의 점수를 조회할 수 있다.")
    @Test
    void nowScore() {
        Pieces pieces = PiecesFactory.createInitialPieces();
        Board board = new Board(pieces);

        Map<Team, Score> score = board.showScore();

        assertThat(score).contains(
                entry(Team.BLACK, new Score(38)),
                entry(Team.WHITE, new Score(38)));
    }

    @DisplayName("체스가 종료되면, 기물들의 점수를 조회할 수 없다.")
    @Test
    void noQueryScore() {
        Pieces pieces = PiecesFactory.createInitialPieces();
        Turn turn = new Turn(Team.WHITE);
        Board endBoard = new Board(new EndBoard(turn, pieces));

        assertThatThrownBy(endBoard::showScore)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 종료된 체스입니다.");
    }

    @DisplayName("체스 결과를 조회할 수 있다.")
    @Test
    void result() {
        Board whiteWinBoard = createWhiteWinBoard();

        ChessResult result = whiteWinBoard.showResult();

        ChessResult expected = new ChessResult(Team.WHITE, Team.BLACK);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("체스가 진행중이라면, 체스 결과를 조회할 수 없다.")
    @Test
    void cantShowResult() {
        Pieces pieces = PiecesFactory.createInitialPieces();
        Board notEndGame = new Board(pieces);

        assertThatThrownBy(notEndGame::showResult)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("아직 진행중인 체스입니다.");
    }

    @DisplayName("체스가 진행중인지 확인할 수 있다.")
    @Test
    void isPlaying() {
        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        Board board = new Board(pieces);

        boolean result = board.isPlaying();

        assertThat(result).isTrue();
    }

    /***
     * ........ 8
     * ........ 7
     * ........ 6
     * ........ 5
     * ........ 4
     * ....K... 3  K: King
     * ...P.... 2  P: Pawn
     * ........ 1
     * --------
     * abcdefgh
     */
    @DisplayName("왕이 죽으면 게임이 종료된다.")
    @Test
    void isEnd() {
        Board board = createWhiteWinBoard();

        boolean result = board.isPlaying();

        assertThat(result).isFalse();
    }

    private Board createWhiteWinBoard() {
        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        Piece sourcePiece = new Pawn(Team.WHITE);
        Piece targetPiece = new King(Team.BLACK);
        Coordinate source = new Coordinate(2, 'd');
        Coordinate target = new Coordinate(3, 'e');
        pieces.put(source, sourcePiece);
        pieces.put(target, targetPiece);
        Board board = new Board(pieces);
        board.move(source, target);

        return board;
    }
}
