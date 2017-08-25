package cn.springmvc.model;

import java.util.Date;

/**
 * Created by YLT on 2017/8/24.
 */
public class Competition {
    private int competitionId;
    private String competitionName;
    private String homeLink;
    private String briefDescription;
    private String competitionSponsor;
    private String competitionType;
    private String competitionStatus;
    private int prize;
    private String prizeCurrency;
    private Date launchTime;
    private Date closeTime;
    private int teamsNum;
    private int competitorsNum;
    private int leaderboardLength;
    private int discussionNum;
    private int kernelNum;
    private String dataIntro;
    private String dataType;
    private String description;
    private String evaluation;

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

    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getCompetitionSponsor() {
        return competitionSponsor;
    }

    public void setCompetitionSponsor(String competitionSponsor) {
        this.competitionSponsor = competitionSponsor;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public String getCompetitionStatus() {
        return competitionStatus;
    }

    public void setCompetitionStatus(String competitionStatus) {
        this.competitionStatus = competitionStatus;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public String getPrizeCurrency() {
        return prizeCurrency;
    }

    public void setPrizeCurrency(String prizeCurrency) {
        this.prizeCurrency = prizeCurrency;
    }

    public Date getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(Date launchTime) {
        this.launchTime = launchTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public int getTeamsNum() {
        return teamsNum;
    }

    public void setTeamsNum(int teamsNum) {
        this.teamsNum = teamsNum;
    }

    public int getCompetitorsNum() {
        return competitorsNum;
    }

    public void setCompetitorsNum(int competitorsNum) {
        this.competitorsNum = competitorsNum;
    }

    public int getLeaderboardLength() {
        return leaderboardLength;
    }

    public void setLeaderboardLength(int leaderboardLength) {
        this.leaderboardLength = leaderboardLength;
    }

    public int getDiscussionNum() {
        return discussionNum;
    }

    public void setDiscussionNum(int discussionNum) {
        this.discussionNum = discussionNum;
    }

    public int getKernelNum() {
        return kernelNum;
    }

    public void setKernelNum(int kernelNum) {
        this.kernelNum = kernelNum;
    }

    public String getDataIntro() {
        return dataIntro;
    }

    public void setDataIntro(String dataIntro) {
        this.dataIntro = dataIntro;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
