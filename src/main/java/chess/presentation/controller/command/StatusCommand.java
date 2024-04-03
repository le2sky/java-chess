package chess.presentation.controller.command;

import chess.application.request.MovePieceRequest;

class StatusCommand implements Command {

    public static boolean canCreate(String input) {
        return "status".equals(input);
    }

    @Override
    public boolean isMove() {
        return false;
    }

    @Override
    public boolean isStatus() {
        return true;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public MovePieceRequest getData() {
        throw new UnsupportedOperationException();
    }
}
