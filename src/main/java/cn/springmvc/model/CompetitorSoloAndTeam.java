package cn.springmvc.model;

/**
 * Created by YLT on 2017/12/19.
 */
public class CompetitorSoloAndTeam {
    private int competitorId;
    private int teamAveRank;
    private int soloAveRank;
    private double teamAve;
    private double soloAve;
    private int teamNum;
    private int soloNum;
    private int teamWin;
    private int soloWin;


    public CompetitorSoloAndTeam(int competitorId, int teamAveRank, int soloAveRank, double teamAve, double soloAve, int teamNum, int soloNum, int teamWin, int soloWin) {
        this.competitorId = competitorId;
        this.teamAveRank = teamAveRank;
        this.soloAveRank = soloAveRank;
        this.teamAve = teamAve;
        this.soloAve = soloAve;
        this.teamNum = teamNum;
        this.soloNum = soloNum;
        this.teamWin = teamWin;
        this.soloWin = soloWin;
    }

    public double getTeamAve() {
        return teamAve;
    }

    public void setTeamAve(double teamAve) {
        this.teamAve = teamAve;
    }

    public double getSoloAve() {
        return soloAve;
    }

    public void setSoloAve(double soloAve) {
        this.soloAve = soloAve;
    }

    public int getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(int competitorId) {
        this.competitorId = competitorId;
    }

    public int getTeamAveRank() {
        return teamAveRank;
    }

    public void setTeamAveRank(int teamAveRank) {
        this.teamAveRank = teamAveRank;
    }

    public int getSoloAveRank() {
        return soloAveRank;
    }

    public void setSoloAveRank(int soloAveRank) {
        this.soloAveRank = soloAveRank;
    }

    public int getSoloNum() {
        return soloNum;
    }

    public void setSoloNum(int soloNum) {
        this.soloNum = soloNum;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public int getSoloWin() {
        return soloWin;
    }

    public void setSoloWin(int soloWin) {
        this.soloWin = soloWin;
    }

    public int getTeamWin() {
        return teamWin;
    }

    public void setTeamWin(int teamWin) {
        this.teamWin = teamWin;
    }


}
