package chess.controller;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import chess.domain.board.Board;
import chess.domain.board.Coordinate;
import chess.domain.piece.Team;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.command.MoveCommand;
import chess.view.command.StartCommand;

public class ChessController {

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
            handleException(this::start, board);
            outro(board);
        }
    }

    private void start(Board board) {
        outputView.printPieces(board.getPieces());
        while (board.isPlaying()) {
            MoveCommand moveCommand = inputView.readMoveCommand();
            if (moveCommand.isEnd()) {
                outputView.printEndMessage();
                break;
            } else if (moveCommand.isStatus()) {
                Map<Team, Double> scoreBoard = Map.of(
                        Team.WHITE, board.nowScore(Team.WHITE),
                        Team.BLACK, board.nowScore(Team.BLACK)
                );
                outputView.printScore(scoreBoard);
            } else {
                Coordinate source = moveCommand.source();
                Coordinate target = moveCommand.target();
                board.move(source, target);
                outputView.printPieces(board.getPieces());
            }
        }
    }

    private void outro(Board board) {
        if (board.isPlaying()) {
            return;
        }

        outputView.printChessResult(board.showResult());
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
