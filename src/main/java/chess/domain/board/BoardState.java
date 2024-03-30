package chess.domain.board;

import chess.domain.piece.Coordinate;
import chess.domain.piece.Pieces;
import chess.domain.piece.Score;
import chess.domain.piece.Team;

interface BoardState {

    BoardState move(Coordinate source, Coordinate target);

    Score nowScore(Team team);

    ChessResult showResult();

    boolean isPlaying();

    Pieces getPieces();
}
