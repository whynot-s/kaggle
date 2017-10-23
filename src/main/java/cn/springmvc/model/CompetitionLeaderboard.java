package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class CompetitionLeaderboard {
    private int competitionId;
    private String competitionName;
    private int ranking;
    private int teamId;
    private String teamName;
    private String teamMember;
    private double score;
    private int entries;
    private int changeFromPublic;
    private String teamMemberId;

    public String getTeamMemberId() {
        return teamMemberId;
    }

    public void setTeamMemberId(String teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public int getChangeFromPublic() {
        return changeFromPublic;
    }

    public void setChangeFromPublic(int changeFromPublic) {
        this.changeFromPublic = changeFromPublic;
    }
}
