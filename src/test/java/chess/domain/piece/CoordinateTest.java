package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CoordinateTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> new Coordinate(1, 'a'))
                .doesNotThrowAnyException();
    }

    @DisplayName("가중치가 더해진 새로운 좌표를 생성할 수 있는지 검사한다.")
    @Test
    void canPlus() {
        Coordinate coordinate = new Coordinate(1, 'a');
        Weight applicableWeight = new Weight(1, 1);
        Weight noApplicableWeight = new Weight(-1, -1);

        Assertions.assertAll(
                () -> Assertions.assertTrue(coordinate.canPlus(applicableWeight)),
                () -> Assertions.assertFalse(coordinate.canPlus(noApplicableWeight))
        );
    }

    @DisplayName("가중치가 더해진 새로운 좌표를 생성할 수 있다.")
    @Test
    void plus() {
        Coordinate coordinate = new Coordinate(1, 'a');
        Weight applicableWeight = new Weight(1, 1);

        Coordinate result = coordinate.plus(applicableWeight);

        Coordinate expected = new Coordinate(2, 'b');
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("가중치를 더했을때, 좌표의 범위가 초과된 새로운 좌표를 생성할 수 없다.")
    @Test
    void cantPlus() {
        Coordinate coordinate = new Coordinate(1, 'a');
        Weight noApplicableWeight = new Weight(-1, -1);

        assertThatThrownBy(() -> coordinate.plus(noApplicableWeight))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 가중치를 더할 수 없습니다.");
    }
}
