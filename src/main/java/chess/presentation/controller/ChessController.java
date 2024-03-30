package chess.presentation.controller;

import java.util.function.Supplier;
import chess.application.ChessService;
import chess.application.request.MovePieceRequest;
import chess.presentation.view.InputView;
import chess.presentation.view.OutputView;
import chess.presentation.controller.command.MoveCommand;
import chess.presentation.controller.command.StartCommand;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessService service;

    public ChessController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = new ChessService();
    }

    public void run() {
        outputView.printStartMessage();
        StartCommand startCommand = handleException1(() -> StartCommand.from(inputView.readCommand()));
        if (startCommand.isStart()) {
            handleException(this::start);
            outro();
        }
    }

    private void start() {
        outputView.printPieces(service.findPieces());
        while (service.isChessPlaying()) {
            MoveCommand moveCommand = handleException2(() -> MoveCommand.from(inputView.readCommand()));
            if (moveCommand.isEnd()) {
                outputView.printEndMessage();
                break;
            } else if (moveCommand.isStatus()) {
                outputView.printScore(service.findChessScore());
            } else {
                service.move(new MovePieceRequest(moveCommand.source(), moveCommand.target()));
                outputView.printPieces(service.findPieces());
            }
        }
    }

    private void outro() {
        if (service.isChessPlaying()) {
            return;
        }

        outputView.printChessResult(service.findChessResult());
    }

    private void handleException(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            handleException(runnable);
        }
    }

    private StartCommand handleException1(Supplier<StartCommand> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            return handleException(supplier);
        }
    }

    private MoveCommand handleException2(Supplier<MoveCommand> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            return handleException(supplier);
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
