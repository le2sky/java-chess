package chess.presentation.view;

import java.util.Map;
import chess.domain.board.ChessResult;
import chess.domain.board.Pieces;
import chess.domain.piece.Team;

public class OutputView {

    private final ViewDataConverter converter = new ViewDataConverter();

    public void printGameGuide() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 점수 조회 : status");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printPieces(Pieces pieces) {
        CharSequence viewData = converter.convertToViewData(pieces);
        System.out.printf("%n%s%n", viewData);
    }

    public void printGameEndMessage() {
        System.out.println("체스 게임을 종료합니다.");
    }

    // TODO : StringBuilder로 개선
    public void printScore(Map<Team, Double> scoreBoard) {
        System.out.printf("%n[점수 현황판]%n");
        scoreBoard.forEach((team, score) -> System.out.printf("%s 팀 점수 : %s %n", team.name(), score));
        System.out.println();
    }

    public void printChessResult(ChessResult chessResult) {
        System.out.printf("%n[게임 결과]%n");
        System.out.printf("승자 : %s%n", chessResult.winner().name());
        System.out.printf("패자 : %s%n", chessResult.loser().name());
        System.out.println();
    }

    public void printExceptionMessage(Exception exception) {
        System.out.printf("%n[ERROR]%n%s%n", exception.getMessage());
    }
}
