package chess.presentation.controller.command;

import chess.application.ChessService;
import chess.presentation.view.OutputView;

class EndCommand extends Command {

    public static boolean canCreate(String input) {
        return "end".equals(input);
    }

    @Override
    public void execute(OutputView outputView, ChessService service) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isExecutable() {
        return false;
    }
}
