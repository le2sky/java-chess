package chess.domain.board;

enum BoardState {

    PLAYING,
    END;

    public boolean isPlaying() {
        return this == PLAYING;
    }
}
