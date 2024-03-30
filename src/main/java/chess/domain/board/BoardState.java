package chess.domain.board;

import chess.domain.piece.Team;

interface BoardState {

    BoardState move(Coordinate source, Coordinate target);

    double nowScore(Team team);

    ChessResult showResult();

    boolean isPlaying();

    Pieces getPieces();
}