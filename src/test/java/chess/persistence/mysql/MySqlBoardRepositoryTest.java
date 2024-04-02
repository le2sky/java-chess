package chess.persistence.mysql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import chess.domain.piece.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MySqlBoardRepositoryTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        List<BoardHistoryEntity> entities = List.of(new BoardHistoryEntity(1, 'a', 2, 'a'));
        SimpleBoardHistoryDao boardHistoryDao = new SimpleBoardHistoryDao(entities);

        assertThatCode(() -> new MySqlBoardRepository(boardHistoryDao))
                .doesNotThrowAnyException();
    }

    @DisplayName("이전에 진행하던 보드가 존재하는지 확인할 수 있다.")
    @Test
    void hasContinuableBoard() {
        List<BoardHistoryEntity> entities = List.of(new BoardHistoryEntity(1, 'a', 2, 'a'));
        SimpleBoardHistoryDao boardHistoryDao = new SimpleBoardHistoryDao(entities);
        MySqlBoardRepository mySqlBoardRepository = new MySqlBoardRepository(boardHistoryDao);

        boolean result = mySqlBoardRepository.hasContinuableBoard();

        assertThat(result).isTrue();
    }

    @DisplayName("이전에 진행하던 보드가 존재하지 않는지 확인할 수 있다.")
    @Test
    void hasNoContinuableBoard() {
        List<BoardHistoryEntity> entities = Collections.emptyList();
        SimpleBoardHistoryDao boardHistoryDao = new SimpleBoardHistoryDao(entities);
        MySqlBoardRepository mySqlBoardRepository = new MySqlBoardRepository(boardHistoryDao);

        boolean result = mySqlBoardRepository.hasContinuableBoard();

        assertThat(result).isFalse();
    }

    @DisplayName("보드를 불러올 수 있다.")
    @Test
    void loadBoard() {
        List<BoardHistoryEntity> entities = List.of(
                new BoardHistoryEntity(2, 'a', 4, 'a'),
                new BoardHistoryEntity(7, 'a', 5, 'a')
        );
        SimpleBoardHistoryDao boardHistoryDao = new SimpleBoardHistoryDao(entities);
        MySqlBoardRepository mySqlBoardRepository = new MySqlBoardRepository(boardHistoryDao);

        assertThatCode(mySqlBoardRepository::loadBoard)
                .doesNotThrowAnyException();
    }

    @DisplayName("잘못된 기보가 저장되어 있으면, 보드를 불러올 수 없다.")
    @Test
    void noLoadBoard() {
        List<BoardHistoryEntity> entities = List.of(
                new BoardHistoryEntity(7, 'a', 5, 'a'),
                new BoardHistoryEntity(2, 'a', 4, 'a')
        );
        SimpleBoardHistoryDao boardHistoryDao = new SimpleBoardHistoryDao(entities);
        MySqlBoardRepository mySqlBoardRepository = new MySqlBoardRepository(boardHistoryDao);

        assertThatThrownBy(mySqlBoardRepository::loadBoard)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("보드를 불러오는데, 실패했습니다.");
    }

    @DisplayName("기물의 이동을 기록할 수 있다.")
    @Test
    void saveMoveHistory() {
        List<BoardHistoryEntity> entities = new ArrayList<>();
        SimpleBoardHistoryDao boardHistoryDao = new SimpleBoardHistoryDao(entities);
        MySqlBoardRepository mySqlBoardRepository = new MySqlBoardRepository(boardHistoryDao);
        Coordinate source = new Coordinate(2, 'a');
        Coordinate target = new Coordinate(4, 'a');

        mySqlBoardRepository.saveMoveHistory(source, target);

        assertThat(entities.size()).isEqualTo(1);
    }

    @DisplayName("모든 기물의 이동 기록을 지울 수 있다.")
    @Test
    void clear() {
        List<BoardHistoryEntity> entities = new ArrayList<>(List.of(
                new BoardHistoryEntity(2, 'a', 4, 'a'),
                new BoardHistoryEntity(7, 'a', 5, 'a')
        ));
        SimpleBoardHistoryDao boardHistoryDao = new SimpleBoardHistoryDao(entities);
        MySqlBoardRepository mySqlBoardRepository = new MySqlBoardRepository(boardHistoryDao);

        mySqlBoardRepository.clear();

        assertThat(entities).isEmpty();
    }
}
