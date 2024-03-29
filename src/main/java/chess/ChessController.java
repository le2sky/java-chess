package chess;

import java.util.function.Consumer;
import java.util.function.Supplier;
import chess.domain.board.Board;
import chess.domain.board.ChessResult;
import chess.domain.board.Coordinate;
import chess.domain.piece.Team;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.command.MoveCommand;
import chess.view.command.StartCommand;

class ChessController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printStartMessage();
        StartCommand startCommand = handleException(inputView::readWannaStart);
        if (startCommand.isStart()) {
            Board board = new Board();
            outputView.printPieces(board.getPieces());
            handleException(this::tryMove, board);
            outro(board);
        }
    }

    private void tryMove(Board board) {
        MoveCommand moveCommand = inputView.readMoveCommand();
        while (!moveCommand.isEnd() && board.isPlaying()) {
            if (moveCommand.isStatus()) {
                System.out.printf("백팀 점수 : %s %n", board.nowScore(Team.WHITE));
                System.out.printf("흑팀 점수 : %s %n", board.nowScore(Team.BLACK));
            } else {
                Coordinate source = moveCommand.source();
                Coordinate target = moveCommand.target();
                board.move(source, target);
                outputView.printPieces(board.getPieces());
            }
            moveCommand = inputView.readMoveCommand();
        }
    }

    private void outro(Board board) {
        if (board.isPlaying()) {
            return;
        }

        ChessResult chessResult = board.showResult();
        System.out.printf("승자 : %s%n", chessResult.winner().name());
        System.out.printf("패자 : %s%n", chessResult.loser().name());
    }

    private <T> void handleException(Consumer<T> consumer, T target) {
        try {
            consumer.accept(target);
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            handleException(consumer, target);
        }
    }

    private <T> T handleException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            return handleException(supplier);
        }
    }
}
