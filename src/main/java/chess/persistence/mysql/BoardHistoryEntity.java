package chess.persistence.mysql;

public class BoardHistoryEntity {

    public int sourceRank;
    public char sourceFile;
    public int targetRank;
    public char targetFile;

    public BoardHistoryEntity(int sourceRank, char sourceFile, int targetRank, char targetFile) {
        this.sourceRank = sourceRank;
        this.sourceFile = sourceFile;
        this.targetRank = targetRank;
        this.targetFile = targetFile;
    }
}
