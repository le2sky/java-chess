package chess.persistence.mysql;

public class BoardHistoryEntity {

    public int sourceRank;
    public int sourceFile;
    public int targetRank;
    public int targetFile;

    public BoardHistoryEntity(int sourceRank, int sourceFile, int targetRank, int targetFile) {
        this.sourceRank = sourceRank;
        this.sourceFile = sourceFile;
        this.targetRank = targetRank;
        this.targetFile = targetFile;
    }
}
