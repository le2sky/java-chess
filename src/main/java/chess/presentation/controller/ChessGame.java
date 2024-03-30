package chess.presentation.controller;

import chess.application.ChessService;

public class ChessGame extends AbstractController {

    private final Controller nextController;

    public ChessGame(ChessService service) {
        this.nextController = new ChessController(service);
    }

    @Override
    public void execute() {
        outputView.printGameGuide();
        handleException(this::selectStartMenu);
    }

    private void selectStartMenu() {
        if (inputView.readWannaPlay()) {
            nextController.execute();
        }

        outputView.printGameEndMessage();
    }
}
