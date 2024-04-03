package chess.domain.piece;

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
        Path forwardPath = createForwardPath(source);
        List<Coordinate> diagonalCoordinates = createDiagonalCoordinates(source);
        validateReachable(target, diagonalCoordinates, forwardPath);
        validateForwardAttack(target, forwardPath, pieces);
        if (isTwoStep(source, target)) {
            validateInitialCoordinate(source);
            validateObstacle(target, forwardPath, pieces);
        }
        validateDiagonal(target, diagonalCoordinates, pieces);
    }

    private Path createForwardPath(Coordinate source) {
        List<Direction> directions = List.of(Direction.UP, Direction.UP_UP);
        return new Path(createCoordinates(source, directions));
    }

    private List<Coordinate> createDiagonalCoordinates(Coordinate source) {
        List<Direction> directions = List.of(Direction.UP_LEFT, Direction.UP_RIGHT);
        return createCoordinates(source, directions);
    }

    private List<Coordinate> createCoordinates(Coordinate source, List<Direction> directions) {
        int forwardDirection = team.getForwardDirection();

        return directions.stream()
                .map(Direction::getWeight)
                .map(weight -> weight.multiplyAtRankWeight(forwardDirection))
                .filter(source::canPlus)
                .map(source::plus)
                .toList();
    }

    private void validateReachable(Coordinate target, List<Coordinate> diagonalCoordinates, Path forwardPath) {
        if (!(forwardPath.contains(target) || diagonalCoordinates.contains(target))) {
            throw new InvalidMoveException();
        }
    }

    private void validateForwardAttack(Coordinate target, Path forwardPath, Pieces pieces) {
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

    private void validateObstacle(Coordinate target, Path forwardPath, Pieces pieces) {
        if (forwardPath.hasObstacle(target, pieces)) {
            throw new ObstacleException();
        }
    }

    private void validateDiagonal(Coordinate target, List<Coordinate> diagonalCoordinates, Pieces pieces) {
        if (diagonalCoordinates.contains(target)) {
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
                .map(direction -> Path.createPath(direction, source))
                .anyMatch(path -> path.hasSamePiece(this, pieces));

        if (hasSamePieceAtFile) {
            return WEEK_SCORE;
        }

        return DEFAULT_SCORE;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
