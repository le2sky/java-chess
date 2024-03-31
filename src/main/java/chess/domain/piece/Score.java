package chess.domain.piece;

public record Score(double value) {

    public Score add(Score other) {
        return new Score(this.value + other.value);
    }
}
