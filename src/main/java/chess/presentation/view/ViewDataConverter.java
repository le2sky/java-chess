package chess.presentation.view;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import chess.domain.board.ChessResult;
import chess.domain.piece.Coordinate;
import chess.domain.piece.Piece;
import chess.domain.piece.Pieces;
import chess.domain.piece.Score;
import chess.domain.piece.Team;

class ViewDataConverter {

    public CharSequence convertToViewData(Pieces pieces) {
        StringBuilder viewData = new StringBuilder();

        for (int rankValue = 8; rankValue >= 1; rankValue--) {
            viewData.append(createRowViewData(pieces, rankValue));
            viewData.append(System.lineSeparator());
        }

        return viewData;
    }

    private String createRowViewData(Pieces pieces, int rankValue) {
        return IntStream.rangeClosed('a', 'h')
                .mapToObj(operand -> new Coordinate(rankValue, (char) operand))
                .map(pieces::findByCoordinate)
                .map(this::convertToViewData)
                .collect(Collectors.joining());
    }

    private String convertToViewData(Piece piece) {
        String shape = PieceShapeSelector.selectShape(piece.getType());
        if (piece.isSameTeam(Team.BLACK)) {
            return shape.toUpperCase();
        }

        return shape.toLowerCase();
    }

    public CharSequence convertToViewData(Map<Team, Score> scoreBoard) {
        StringBuilder viewData = new StringBuilder("[점수 현황판]");
        viewData.append(System.lineSeparator());
        scoreBoard.forEach((team, score) -> {
            viewData.append(team.name());
            viewData.append(" 진영 : ");
            viewData.append(score.value());
            viewData.append("점");
            viewData.append(System.lineSeparator());
        });

        return viewData;
    }

    public CharSequence convertToViewData(ChessResult chessResult) {
        StringBuilder viewData = new StringBuilder("[게임 결과]");
        viewData.append(System.lineSeparator());
        viewData.append("승자 : ");
        viewData.append(chessResult.winner().name());
        viewData.append(System.lineSeparator());
        viewData.append("패자 : ");
        viewData.append(chessResult.loser().name());
        viewData.append(System.lineSeparator());

        return viewData;
    }
}
