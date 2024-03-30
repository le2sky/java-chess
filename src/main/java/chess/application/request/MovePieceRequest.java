package chess.application.request;

import chess.domain.piece.Coordinate;

public record MovePieceRequest(Coordinate source, Coordinate target) {
}
