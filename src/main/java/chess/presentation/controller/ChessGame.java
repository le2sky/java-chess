package chess.presentation.controller;

import chess.application.ChessService;
import chess.presentation.controller.command.Command;
import chess.presentation.controller.command.CommandFactory;

public class ChessGame extends AbstractChessGame {

    private final ChessService service;

    public ChessGame(ChessService service) {
        this.service = service;
    }

    public void execute() {
        outputView.printGameGuide();
        if (handleException(inputView::readWannaPlay)) {
            selectContinueGame();
            outputView.printPieces(service.findPieces());
            handleException(this::tryMove);
        }
        outro();
    }

    private void selectContinueGame() {
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
}
