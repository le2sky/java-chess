package chess.domain.board;

import java.util.Map;
import chess.domain.piece.Coordinate;
import chess.domain.piece.Pieces;
import chess.domain.piece.Score;
import chess.domain.piece.Team;

interface MutableBoard {

    MutableBoard move(Coordinate source, Coordinate target);

    Map<Team, Score> showScore();

    ChessResult showResult();

    boolean isPlaying();

    Pieces getPieces();
}
