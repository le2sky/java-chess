package chess.domain.piece;

import java.util.ArrayList;
import java.util.List;
import chess.domain.board.Coordinate;
import chess.domain.board.Pieces;
import chess.domain.board.PiecesFactory;
import chess.domain.piece.exception.InvalidMoveException;
import chess.domain.piece.exception.ObstacleException;

public class Pawn extends AbstractPiece {

    private static final double WEEK_SCORE = 0.5;
    private static final double DEFAULT_SCORE = 1;

    private final List<Direction> forwardDirections = List.of(Direction.UP, Direction.UP_UP);
    private final List<Direction> diagonalDirections = List.of(Direction.UP_LEFT, Direction.UP_RIGHT);

    public Pawn(Team team) {
        super(PieceType.PAWN, team);
    }

    @Override
    void validatePieceMoveRule(Coordinate source, Coordinate target, Pieces pieces) {
        List<Coordinate> forwardPath = createPath(source, forwardDirections);
        List<Coordinate> diagonalPath = createPath(source, diagonalDirections);

        validateReachable(target, diagonalPath, forwardPath);
        validateForwardAttack(target, forwardPath, pieces);
        if (isTwoStep(source, target)) {
            validateInitialCoordinate(source);
            validateBlocked(target, forwardPath, pieces);
        }
        validateDiagonal(target, diagonalPath, pieces);
    }

    private List<Coordinate> createPath(Coordinate source, List<Direction> directions) {
        int forwardDirection = team.getForwardDirection();

        return directions.stream()
                .map(Direction::getWeight)
                .map(weight -> weight.multiplyAtRankWeight(forwardDirection))
                .filter(source::canPlus)
                .map(source::plus)
                .toList();
    }

    private void validateReachable(Coordinate target, List<Coordinate> diagonalPath, List<Coordinate> forwardPath) {
        if (!(forwardPath.contains(target) || diagonalPath.contains(target))) {
            throw new InvalidMoveException();
        }
    }

    private void validateForwardAttack(Coordinate target, List<Coordinate> forwardPath, Pieces pieces) {
        if (forwardPath.contains(target) && isEnemy(pieces.findByCoordinate(target))) {
            throw new ObstacleException();
        }
    }

    private boolean isTwoStep(Coordinate source, Coordinate target) {
        return Math.abs(source.getRank() - target.getRank()) == 2;
    }

    private void validateInitialCoordinate(Coordinate source) {
        if (!PiecesFactory.isInitialRank(source)) {
            throw new IllegalStateException("초기 상태의 폰이 아닌 경우, 2칸 이동할 수 없습니다.");
        }
    }

    // TODO : 이름 변경할 것
    private void validateBlocked(Coordinate target, List<Coordinate> path, Pieces pieces) {
        Coordinate blockedCoordinate = path.stream()
                .filter(pieces::isPiecePresent)
                .findFirst()
                .orElse(target);

        if (!blockedCoordinate.equals(target)) {
            throw new ObstacleException();
        }
    }

    private void validateDiagonal(Coordinate target, List<Coordinate> diagonalPath, Pieces pieces) {
        if (diagonalPath.contains(target)) {
            validateEnemyExist(target, pieces);
        }
    }

    private void validateEnemyExist(Coordinate target, Pieces pieces) {
        if (!pieces.isPiecePresent(target)) {
            throw new InvalidMoveException();
        }

        if (!isEnemy(pieces.findByCoordinate(target))) {
            throw new InvalidMoveException();
        }
    }

    @Override
    public double calculateScore(Coordinate source, Pieces pieces) {
        List<Direction> checkDirections = List.of(Direction.UP, Direction.DOWN);
        boolean hasSamePieceAtFile = checkDirections.stream()
                .anyMatch(checkDirection -> hasSamePiece(source, checkDirection, pieces));

        if (hasSamePieceAtFile) {
            return WEEK_SCORE;
        }

        return DEFAULT_SCORE;
    }

    private boolean hasSamePiece(Coordinate source, Direction checkDirection, Pieces pieces) {
        Weight weight = checkDirection.getWeight();
        List<Coordinate> path = createPath(weight, source);

        return path.stream()
                .map(pieces::findByCoordinate)
                .anyMatch(this::equals);
    }

    // TODO: 중복 코드 제거 가능할 듯
    private List<Coordinate> createPath(Weight weight, Coordinate nowCoordinate) {
        List<Coordinate> path = new ArrayList<>();

        while (nowCoordinate.canPlus(weight)) {
            nowCoordinate = nowCoordinate.plus(weight);
            path.add(nowCoordinate);
        }

        return path;
    }
}
