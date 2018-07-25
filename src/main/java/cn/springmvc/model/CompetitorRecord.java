package cn.springmvc.model;

/**
 * Created by YLT on 2017/12/19.
 */
public class CompetitorRecord {
    private int competitionId;
    private int competitorId;
    private int ranking;
    private double rankPercent;
    private int teamOrNot;

    public CompetitorRecord(int competitionId, int competitorId, int ranking, int teamOrNot) {
        this.competitionId = competitionId;
        this.competitorId = competitorId;
        this.ranking = ranking;
        this.teamOrNot = teamOrNot;
    }

    public CompetitorRecord(){}

    public double getRankPercent() {
        return rankPercent;
    }

    public void setRankPercent(double rankPercent) {
        this.rankPercent = rankPercent;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public int getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(int competitorId) {
        this.competitorId = competitorId;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getTeamOrNot() {
        return teamOrNot;
    }

    public void setTeamOrNot(int teamOrNot) {
        this.teamOrNot = teamOrNot;
    }
}
