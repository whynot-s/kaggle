package cn.springmvc.model;

/**
 * Created by YLT on 2017/12/23.
 */
public class WhoWantCompete {
    private int competitionId;
    private int competitorId;
    private int discussionId;
    private int sponsor;
    private int inOrNot;
    private int teamOrNot;

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
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

    public void setCompetitorId(int competitiorId) {
        this.competitorId = competitiorId;
    }

    public int getSponsor() {
        return sponsor;
    }

    public void setSponsor(int sponsor) {
        this.sponsor = sponsor;
    }

    public int getInOrNot() {
        return inOrNot;
    }

    public void setInOrNot(int inOrNot) {
        this.inOrNot = inOrNot;
    }

    public int getTeamOrNot() {
        return teamOrNot;
    }

    public void setTeamOrNot(int teamOrNot) {
        this.teamOrNot = teamOrNot;
    }
}
