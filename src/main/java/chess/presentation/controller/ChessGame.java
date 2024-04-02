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
            choiceContinueGame();
            outputView.printPieces(service.findPieces());
            handleException(this::tryMove);
        }
        outro();
    }

    private void choiceContinueGame() {
        if (isContinuable()) {
            outputView.printContinueMessage();
            return;
        }
        service.clearPreviousBoard();
        outputView.printNewGameMessage();
    }

    private boolean isContinuable() {
        if (service.hasContinuableBoard()) {
            outputView.printContinueGuide();
            return handleException(inputView::readWannaContinue);
        }

        return false;
    }

    private void tryMove() {
        Command command;
        do {
            command = CommandFactory.createCommand(inputView.readLine());
            tryExecute(command);
        } while (service.isChessPlaying() && command.isExecutable());
    }

    private void tryExecute(Command command) {
        if (command.isExecutable()) {
            command.execute(outputView, service);
        }
    }

    private void outro() {
        if (!service.isChessPlaying()) {
            outputView.printChessResult(service.findChessResult());
            service.clearPreviousBoard();
        }
        outputView.printGameEndMessage();
    }

    protected void handleException(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            handleException(runnable);
        }
    }

    protected <T> T handleException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            return handleException(supplier);
        }
    }
}
