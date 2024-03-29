package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.Team;

class Turn {

    private Team nowTurn;

    public Turn(Team team) {
        this.nowTurn = team;
    }

    public boolean isSameTeam(Piece piece) {
        return piece.isSameTeam(nowTurn);
    }

    public void change() {
        this.nowTurn = nowTurn.opposite();
    }
}
