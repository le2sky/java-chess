package chess.presentation.controller.command;

import chess.application.ChessService;
import chess.presentation.view.OutputView;

public abstract class Command {

    public abstract void execute(OutputView outputView, ChessService service);

    public abstract boolean isExecutable();
}
