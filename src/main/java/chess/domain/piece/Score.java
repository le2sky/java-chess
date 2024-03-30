package chess.domain.piece;

public class Score {

    private final double value;

    public Score(double score) {
        this.value = score;
    }

    public Score add(Score other) {
        return new Score(this.value + other.value);
    }

    public double getValue() {
        return value;
    }
}
