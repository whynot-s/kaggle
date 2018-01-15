package cn.springmvc.model;

/**
 * Created by YLT on 2017/12/26.
 */
public class AbilityImprove {
    private int competitorId;
    private int teamNum;
    private int soloNum;
    private double teamScore;
    private double soloScore;


    public int getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(int competitorId) {
        this.competitorId = competitorId;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public int getSoloNum() {
        return soloNum;
    }

    public void setSoloNum(int soloNum) {
        this.soloNum = soloNum;
    }

    public double getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(double teamScore) {
        this.teamScore = teamScore;
    }

    public double getSoloScore() {
        return soloScore;
    }

    public void setSoloScore(double soloScore) {
        this.soloScore = soloScore;
    }
}
