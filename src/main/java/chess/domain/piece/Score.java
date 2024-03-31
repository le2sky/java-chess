package chess.domain.piece;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return Double.compare(score.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
