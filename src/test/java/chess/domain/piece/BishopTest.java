package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.List;
import chess.domain.board.Coordinate;
import chess.domain.board.Pieces;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class BishopTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> new Bishop(Team.WHITE))
                .doesNotThrowAnyException();
    }

    @DisplayName("비숍의 점수를 계산한다.")
    @Test
    void calculateScore() {
        Bishop bishop = new Bishop(Team.WHITE);
        HashMap<Coordinate, Piece> piecesMap = new HashMap<>();
        Pieces pieces = new Pieces(piecesMap);
        Coordinate source = new Coordinate(3, 'e');

        double result = bishop.calculateScore(source, pieces);

        assertThat(result).isEqualTo(3);
    }

    @DisplayName("target 좌표에 아군 기물이 있다면, 이동할 수 없다.")
    @Test
    void attackTeam() {
        Bishop bishop = new Bishop(Team.WHITE);
        HashMap<Coordinate, Piece> piecesMap = new HashMap<>();
        Coordinate source = new Coordinate(3, 'e');
        Coordinate target = new Coordinate(4, 'd');
        piecesMap.put(source, bishop);
        piecesMap.put(target, new Pawn(Team.WHITE));
        Pieces pieces = new Pieces(piecesMap);

        assertThatThrownBy(() -> bishop.validateMovable(source, target, pieces))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("아군 기물은 공격할 수 없습니다.");
    }

    @DisplayName("제자리 이동을 할 수 없다.")
    @Test
    void sameCoordinate() {
        Bishop bishop = new Bishop(Team.WHITE);
        HashMap<Coordinate, Piece> piecesMap = new HashMap<>();
        Coordinate source = new Coordinate(3, 'e');
        piecesMap.put(source, bishop);
        Pieces pieces = new Pieces(piecesMap);

        assertThatThrownBy(() -> bishop.validateMovable(source, source, pieces))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("제자리 이동은 할 수 없습니다.");
    }

    /***
     * ........ 8
     * T....... 7  T: target
     * ........ 6
     * ..O..... 5  O: Obstacle
     * ........ 4
     * ....B... 3  B: Bishop
     * ........ 2
     * ........ 1
     * --------
     * abcdefgh
     */
    @DisplayName("target 으로 가는 경로에 기물이 존재하면, 이동할 수 없다.")
    @Test
    void obstacle() {
        Bishop bishop = new Bishop(Team.WHITE);
        HashMap<Coordinate, Piece> piecesMap = new HashMap<>();
        Coordinate source = new Coordinate(3, 'e');
        Coordinate obstacle = new Coordinate(5, 'c');
        Coordinate target = new Coordinate(7, 'a');
        piecesMap.put(source, bishop);
        piecesMap.put(obstacle, new Pawn(Team.WHITE));
        piecesMap.put(target, new Pawn(Team.BLACK));
        Pieces pieces = new Pieces(piecesMap);

        assertThatThrownBy(() -> bishop.validateMovable(source, target, pieces))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("기물로 막혀있어 이동할 수 없습니다.");
    }

    @DisplayName("비숍은 대각으로 제한없이 움직일 수 있다.")
    @MethodSource("createTargetForHappyCase")
    @ParameterizedTest
    void canGo(Coordinate target) {
        Bishop bishop = new Bishop(Team.WHITE);
        HashMap<Coordinate, Piece> piecesMap = new HashMap<>();
        Coordinate source = new Coordinate(3, 'e');
        piecesMap.put(source, bishop);
        Pieces pieces = new Pieces(piecesMap);

        assertThatCode(() -> bishop.validateMovable(source, target, pieces))
                .doesNotThrowAnyException();
    }

    @DisplayName("비숍이 갈 수 없는 위치라면 이동할 수 없다.")
    @MethodSource("createTargetForExceptionCase")
    @ParameterizedTest
    void cantGo(Coordinate target) {
        Bishop bishop = new Bishop(Team.WHITE);
        HashMap<Coordinate, Piece> piecesMap = new HashMap<>();
        Coordinate source = new Coordinate(3, 'e');
        piecesMap.put(source, bishop);
        Pieces pieces = new Pieces(piecesMap);

        assertThatThrownBy(() -> bishop.validateMovable(source, target, pieces))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 기물은 주어진 좌표로 이동할 수 없습니다.");
    }

    /***
     * ........ 8
     * T....... 7  T: target
     * ........ 6
     * ........ 5
     * ........ 4
     * ....Q... 3  B: Bishop
     * ........ 2
     * ........ 1
     * --------
     * abcdefgh
     */
    @DisplayName("비숍의 이동 범위 안에 상대 기물이 있다면, 기물을 잡을 수 있다.")
    @Test
    void attack() {
        Bishop bishop = new Bishop(Team.WHITE);
        Queen enemy = new Queen(Team.BLACK);
        HashMap<Coordinate, Piece> piecesMap = new HashMap<>();
        Coordinate source = new Coordinate(3, 'e');
        Coordinate target = new Coordinate(7, 'a');
        piecesMap.put(source, bishop);
        piecesMap.put(target, enemy);

        Pieces pieces = new Pieces(piecesMap);

        assertThatCode(() -> bishop.validateMovable(source, target, pieces))
                .doesNotThrowAnyException();
    }

    /***
     * ........ 8
     * O....... 7
     * .O.....O 6
     * ..O...O. 5
     * ...O.O.. 4
     * ....B... 3
     * ...O.O.. 2
     * ..O...O. 1
     * --------
     * abcdefgh
     */
    private static Iterable<Coordinate> createTargetForHappyCase() {
        return List.of(
                new Coordinate(4, 'd'),
                new Coordinate(5, 'c'),
                new Coordinate(6, 'b'),
                new Coordinate(7, 'a'),
                new Coordinate(4, 'f'),
                new Coordinate(5, 'g'),
                new Coordinate(6, 'h'),
                new Coordinate(2, 'd'),
                new Coordinate(1, 'c'),
                new Coordinate(2, 'f'),
                new Coordinate(1, 'g'));
    }

    /***
     * ........ 8
     * O....... 7
     * .O.....O 6
     * ..O...O. 5
     * ...OXO.. 4
     * ...XBX.. 3
     * ...OXO.. 2
     * ..O...O. 1
     * --------
     * abcdefgh
     */
    private static Iterable<Coordinate> createTargetForExceptionCase() {
        return List.of(
                new Coordinate(3, 'd'),
                new Coordinate(3, 'f'),
                new Coordinate(4, 'e'),
                new Coordinate(2, 'e'));
    }
}
