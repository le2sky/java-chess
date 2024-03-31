package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PathTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        List<Coordinate> coordinates = List.of(new Coordinate(1, 'a'));

        assertThatCode(() -> new Path(coordinates))
                .doesNotThrowAnyException();
    }

    @DisplayName("시작 위치를 제외한 주어진 방향의 경로를 생성할 수 있다.")
    @Test
    void createWith() {
        Coordinate source = new Coordinate(5, 'a');

        assertThatCode(() -> Path.createPath(Direction.UP, source))
                .doesNotThrowAnyException();
    }

    @DisplayName("주어진 좌표가 경로에 포함되어 있는지 확인할 수 있다.")
    @Test
    void contains() {
        Coordinate target = new Coordinate(1, 'a');
        Coordinate strange = new Coordinate(2, 'a');
        List<Coordinate> coordinates = List.of(target);
        Path path = new Path(coordinates);

        Assertions.assertAll(
                () -> Assertions.assertTrue(path.contains(target)),
                () -> Assertions.assertFalse(path.contains(strange))
        );
    }

    @DisplayName("주어진 타겟 좌표로 가는 중 방해물이 있는지 확인할 수 있다.")
    @Test
    void hasObstacle() {
        Coordinate obstacle = new Coordinate(3, 'c');
        Coordinate target = new Coordinate(3, 'd');
        Coordinate noObstacleTarget = new Coordinate(3, 'b');
        List<Coordinate> coordinates = List.of(
                new Coordinate(3, 'a'),
                noObstacleTarget,
                obstacle,
                target);
        HashMap<Coordinate, Piece> pieceMap = new HashMap<>();
        pieceMap.put(obstacle, new Pawn(Team.WHITE));
        Pieces pieces = new Pieces(pieceMap);
        Path path = new Path(coordinates);

        Assertions.assertAll(
                () -> Assertions.assertTrue(path.hasObstacle(target, pieces)),
                () -> Assertions.assertFalse(path.hasObstacle(noObstacleTarget, pieces))
        );
    }

    @DisplayName("주어진 피스가 경로에 존재하는지 확인할 수 있다.")
    @Test
    void hasSamePiece() {
        Coordinate source = new Coordinate(3, 'c');
        Coordinate duplicated = new Coordinate(3, 'b');
        Coordinate other = new Coordinate(3, 'b');
        List<Coordinate> coordinates = List.of(
                source,
                duplicated,
                other);
        HashMap<Coordinate, Piece> pieceMap = new HashMap<>();
        pieceMap.put(source, new Pawn(Team.WHITE));
        pieceMap.put(duplicated, new Pawn(Team.WHITE));
        pieceMap.put(other, new King(Team.WHITE));
        Pieces pieces = new Pieces(pieceMap);
        Path path = new Path(coordinates);

        Assertions.assertAll(
                () -> Assertions.assertTrue(path.hasSamePiece(new Pawn(Team.WHITE), pieces)),
                () -> Assertions.assertFalse(path.hasSamePiece(new Rook(Team.WHITE), pieces))
        );
    }
}
