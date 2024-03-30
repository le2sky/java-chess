package chess.domain.piece;

import java.util.ArrayList;
import java.util.List;
import chess.domain.piece.exception.InvalidMoveException;
import chess.domain.piece.exception.ObstacleException;

public class Pawn extends AbstractPiece {

    private static final Score WEEK_SCORE = new Score(0.5);
    private static final Score DEFAULT_SCORE = new Score(1);

    public Pawn(Team team) {
        super(PieceType.PAWN, team);
    }

    @Override
    void validatePieceMoveRule(Coordinate source, Coordinate target, Pieces pieces) {
        List<Coordinate> forwardPath = createForwardPossibleCoordinates(source);
        List<Coordinate> diagonalPossibleCoordinates = createDiagonalPossibleCoordinate(source);
        validateReachable(target, diagonalPossibleCoordinates, forwardPath);
        validateForwardAttack(target, forwardPath, pieces);
        if (isTwoStep(source, target)) {
            validateInitialCoordinate(source);
            validateObstacle(forwardPath.get(0), pieces);
        }
        validateDiagonal(target, diagonalPossibleCoordinates, pieces);
    }

    private List<Coordinate> createForwardPossibleCoordinates(Coordinate source) {
        List<Direction> directions = List.of(Direction.UP, Direction.UP_UP);

        return createPossibleCoordinate(source, directions);
    }

    private List<Coordinate> createDiagonalPossibleCoordinate(Coordinate source) {
        List<Direction> directions = List.of(Direction.UP_LEFT, Direction.UP_RIGHT);

        return createPossibleCoordinate(source, directions);
    }

    private List<Coordinate> createPossibleCoordinate(Coordinate source, List<Direction> directions) {
        int forwardDirection = team.getForwardDirection();

        return directions.stream()
                .map(Direction::getWeight)
                .map(weight -> weight.multiplyAtRankWeight(forwardDirection))
                .filter(source::canPlus)
                .map(source::plus)
                .toList();
    }

    private void validateReachable(
            Coordinate target,
            List<Coordinate> diagonalPossibleCoordinates,
            List<Coordinate> forwardPath) {
        if (!(forwardPath.contains(target) || diagonalPossibleCoordinates.contains(target))) {
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

    private void validateObstacle(Coordinate middleCoordinate, Pieces pieces) {
        if(pieces.isPiecePresent(middleCoordinate)){
            throw new ObstacleException();
        }
    }

    private void validateDiagonal(
            Coordinate target,
            List<Coordinate> diagonalPossibleCoordinates,
            Pieces pieces) {
        if (diagonalPossibleCoordinates.contains(target)) {
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
    public Score calculateScore(Coordinate source, Pieces pieces) {
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

    private List<Coordinate> createPath(Weight weight, Coordinate nowCoordinate) {
        List<Coordinate> path = new ArrayList<>();
        while (nowCoordinate.canPlus(weight)) {
            nowCoordinate = nowCoordinate.plus(weight);
            path.add(nowCoordinate);
        }

        return path;
    }
}
