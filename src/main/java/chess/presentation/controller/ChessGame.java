package chess.presentation.controller;

import java.util.function.Supplier;
import chess.application.ChessService;
import chess.presentation.controller.command.Command;
import chess.presentation.controller.command.CommandFactory;
import chess.presentation.view.InputView;
import chess.presentation.view.OutputView;

public class ChessGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessService service;

    public ChessGame(ChessService service) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.service = service;
    }

    public void execute() {
        outputView.printGameGuide();
        if (handleException(inputView::readWannaPlay)) {
            startGame();
        }
        outro();
    }

    private void startGame() {
        if (isContinuable()) {
            continueGame();
            return;
        }
        startNewGame();
    }

    private boolean isContinuable() {
        if (service.hasContinuableBoard()) {
            outputView.printContinueGuide();
            return handleException(inputView::readWannaContinue);
        }

        return false;
    }

    private void continueGame() {
        outputView.printContinueMessage();
        outputView.printPieces(service.findPieces());
        handleException(this::tryMove);
    }

    private void startNewGame() {
        service.clearPreviousBoard();
        outputView.printNewGameMessage();
        outputView.printPieces(service.findPieces());
        handleException(this::tryMove);
    }

    private void tryMove() {
        Command command;
        do {
            command = CommandFactory.createCommand(inputView.readLine());
            process(command);
        } while (service.isChessPlaying() && !command.isEnd());
    }

    private void process(Command command) {
        if (command.isMove()) {
            service.move(command.getData());
            outputView.printPieces(service.findPieces());
        }

        if (command.isStatus()) {
            outputView.printScore(service.findChessScore());
        }
    }

    private void outro() {
        if (!service.isChessPlaying()) {
            outputView.printChessResult(service.findChessResult());
            service.clearPreviousBoard();
        }
        outputView.printGameEndMessage();
    }

    private void handleException(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            handleException(runnable);
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
