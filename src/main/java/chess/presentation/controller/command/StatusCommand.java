package chess.presentation.controller.command;

import chess.application.ChessService;
import chess.presentation.view.OutputView;

class StatusCommand extends Command {

    public static boolean canCreate(String input) {
        return "status".equals(input);
    }

    @Override
    public void execute(OutputView outputView, ChessService service) {
        outputView.printScore(service.findChessScore());
    }

    @Override
    public boolean isExecutable() {
        return true;
    }
}
