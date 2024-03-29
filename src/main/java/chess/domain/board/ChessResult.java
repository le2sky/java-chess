package chess.domain.board;

import chess.domain.piece.Team;

public record ChessResult(Team winner, Team loser) {
}
