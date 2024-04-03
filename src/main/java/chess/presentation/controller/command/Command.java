package chess.presentation.controller.command;

import chess.application.request.MovePieceRequest;

public interface Command {

    boolean isMove();

    boolean isStatus();

    boolean isEnd();

    MovePieceRequest getData();
}
