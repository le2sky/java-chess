package chess.presentation.controller;

import chess.application.ChessService;
import chess.presentation.controller.command.Command;
import chess.presentation.controller.command.CommandFactory;

class ChessController extends AbstractController {

    private final ChessService service;

    public ChessController(ChessService chessService) {
        this.service = chessService;
    }

    @Override
    public void execute() {
        selectGameContinue();
        outputView.printPieces(service.findPieces());
        handleException(this::tryMove);
    }

    private void selectGameContinue() {
        if (service.hasContinuableBoard() && handleException(inputView::readWannaContinue)) {
            outputView.printContinueMessage();
            return;
        }

        service.clearPreviousBoard();
        outputView.printNewGameMessage();
    }

    private void tryMove() {
        Command command;

        do {
            command = createCommand();
            executeCommand(command);
        } while (service.isChessPlaying() && command.isExecutable());

        if (!service.isChessPlaying()) {
            outputView.printChessResult(service.findChessResult());
            service.clearPreviousBoard();
        }
    }

    private Command createCommand() {
        return handleException(() -> CommandFactory.createCommand(inputView.readLine()));
    }

    private void executeCommand(Command command) {
        if (command.isExecutable()) {
            command.execute(outputView, service);
        }
    }
}
