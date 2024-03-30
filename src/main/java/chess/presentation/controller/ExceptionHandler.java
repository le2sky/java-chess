package chess.presentation.controller;

import java.util.function.Supplier;
import chess.presentation.controller.command.MoveCommand;
import chess.presentation.controller.command.StartCommand;
import chess.presentation.view.OutputView;

class ExceptionHandler {

    private ExceptionHandler() {
    }

    public static void handleException(Runnable runnable, OutputView outputView) {
        try {
            runnable.run();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            handleException(runnable, outputView);
        }
    }

    public static StartCommand handleException1(Supplier<StartCommand> supplier, OutputView outputView) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            return handleException1(supplier, outputView);
        }
    }

    public static MoveCommand handleException2(Supplier<MoveCommand> supplier, OutputView outputView) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            outputView.printExceptionMessage(exception);
            return handleException2(supplier, outputView);
        }
    }
}
