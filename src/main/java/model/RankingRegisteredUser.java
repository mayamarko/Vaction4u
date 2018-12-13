package model;

public class RankingRegisteredUser extends Ranking {
    private int rank;
    private String[] literalRanking;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String[] getLiteralRanking() {
        return literalRanking;
    }

    public void setLiteralRanking(String[] literalRanking) {
        this.literalRanking = literalRanking;
    }
}
