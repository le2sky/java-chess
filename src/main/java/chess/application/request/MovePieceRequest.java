package chess.application.request;

import chess.domain.board.Coordinate;

public record MovePieceRequest(Coordinate source, Coordinate target) {
}
