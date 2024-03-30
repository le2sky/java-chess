package chess;

import chess.application.ChessService;
import chess.persistence.mysql.MySqlBoardRepository;
import chess.presentation.controller.ChessGame;

class Main {

    public static void main(String[] args) {
        ChessService chessService = new ChessService(new MySqlBoardRepository());
        ChessGame chessGame = new ChessGame(chessService);

        chessGame.execute();
    }
}
