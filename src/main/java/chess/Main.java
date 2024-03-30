package chess;

import chess.application.ChessService;
import chess.persistece.memory.MemoryBoardRepository;
import chess.presentation.controller.ChessGame;

class Main {

    public static void main(String[] args) {
        ChessService chessService = new ChessService(new MemoryBoardRepository());
        ChessGame chessGame = new ChessGame(chessService);

        chessGame.execute();
    }
}
