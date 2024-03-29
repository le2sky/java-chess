package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.Team;

class Turn {

    private Team team;

    public Turn(Team team) {
        this.team = team;
    }

    public boolean isSameTeam(Piece piece) {
        return piece.isSameTeam(team);
    }

    public void change() {
        this.team = team.opposite();
    }

    public Team getNowTeam() {
        return team;
    }
}
