package chess.presentation.controller;

import java.util.function.Supplier;
import chess.presentation.view.InputView;
import chess.presentation.view.OutputView;

abstract class AbstractChessGame {

    protected final InputView inputView;
    protected final OutputView outputView;

    protected AbstractChessGame() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
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
