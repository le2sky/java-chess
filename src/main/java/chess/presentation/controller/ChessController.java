package chess.presentation.controller;

import chess.application.ChessService;
import chess.application.request.MovePieceRequest;
import chess.presentation.controller.command.MoveCommand;
import chess.presentation.controller.command.StartCommand;
import chess.presentation.view.InputView;
import chess.presentation.view.OutputView;

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
        StartCommand startCommand = ExceptionHandler.handleException1(() -> StartCommand.from(inputView.readCommand()), outputView);
        if (startCommand.isStart()) {
            ExceptionHandler.handleException(this::start, outputView);
            outro();
        }
    }

    private void start() {
        outputView.printPieces(service.findPieces());
        while (service.isChessPlaying()) {
            MoveCommand moveCommand = ExceptionHandler.handleException2(() -> MoveCommand.from(inputView.readCommand()), outputView);
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
}
