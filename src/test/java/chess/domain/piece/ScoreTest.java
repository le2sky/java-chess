package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> new Score(1.0))
                .doesNotThrowAnyException();
    }

    @DisplayName("다른 점수와 더할 수 있다.")
    @Test
    void add() {
        Score score = new Score(1.0);
        Score other = new Score(1.0);

        Score result = score.add(other);

        assertThat(result.value()).isEqualTo(2);
    }
}
