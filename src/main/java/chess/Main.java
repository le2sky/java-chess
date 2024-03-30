package chess;

import chess.presentation.controller.ChessController;
import chess.presentation.view.InputView;
import chess.presentation.view.OutputView;

class Main {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ChessController controller = new ChessController(inputView, outputView);

        controller.run();
    }
}
