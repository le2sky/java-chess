package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThatCode;

import chess.domain.piece.Pawn;
import chess.domain.piece.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> new Turn(Team.WHITE))
                .doesNotThrowAnyException();
    }

    @DisplayName("주어진 피스의 턴인지 확인할 수 있다.")
    @Test
    void isSameTeam() {
        Turn whiteTurn = new Turn(Team.WHITE);
        Pawn whitePawn = new Pawn(Team.WHITE);
        Pawn blackPawn = new Pawn(Team.BLACK);

        Assertions.assertAll(
                () -> Assertions.assertTrue(whiteTurn.isSameTeam(whitePawn)),
                () -> Assertions.assertFalse(whiteTurn.isSameTeam(blackPawn))
        );
    }

    @DisplayName("턴을 변경할 수 있다.")
    @Test
    void switchTurn() {
        Turn turn = new Turn(Team.WHITE);

        assertThatCode(turn::change)
                .doesNotThrowAnyException();
    }
}
