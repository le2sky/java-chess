package chess.presentation.controller.command;

import chess.application.request.MovePieceRequest;

class EndCommand implements Command {

    public static boolean canCreate(String input) {
        return "end".equals(input);
    }

    @Override
    public boolean isMove() {
        return false;
    }

    @Override
    public boolean isStatus() {
        return false;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public MovePieceRequest getData() {
        throw new UnsupportedOperationException();
    }
}
