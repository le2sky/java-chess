package chess.presentation.view;

import java.util.Map;
import chess.domain.board.ChessResult;
import chess.domain.piece.Pieces;
import chess.domain.piece.Score;
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

    public void printContinueGuide() {
        System.out.println("> 이전에 진행하던 게임이 존재합니다. 불러오겠습니까?");
        System.out.println("> 이전 계속 진행 : yes");
        System.out.println("> 새로운 게임 시작 : no");
    }

    public void printNewGameMessage() {
        System.out.println("신규 게임을 진행합니다.");
    }

    public void printContinueMessage() {
        System.out.println("이전에 진행하던 게임을 불러왔습니다.");
    }

    public void printGameEndMessage() {
        System.out.println("체스 게임을 종료합니다.");
    }

    public void printPieces(Pieces pieces) {
        CharSequence viewData = converter.convertToViewData(pieces);
        printViewData(viewData);
    }

    public void printChessResult(ChessResult chessResult) {
        CharSequence viewData = converter.convertToViewData(chessResult);
        printViewData(viewData);
    }

    public void printScore(Map<Team, Score> chessScore) {
        CharSequence viewData = converter.convertToViewData(chessScore);
        printViewData(viewData);
    }

    private void printViewData(CharSequence viewData) {
        System.out.printf("%n%s%n", viewData);
    }

    public void printExceptionMessage(Exception exception) {
        System.out.printf("%n[ERROR]%n%s%n", exception.getMessage());
    }
}
