package cn.springmvc.model;

/**
 * Created by YLT on 2018/1/24.
 */
public class TeamRecordAnalysis {
    private int member1;
    private int teamNo;
    private int teamMemberSum;
    private String teamMember;
    private double cost;
    private double diff;
    private double grow;
    private double successRate;


    public int getMember1() {
        return member1;
    }

    public void setMember1(int member1) {
        this.member1 = member1;
    }

    public int getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(int teamNo) {
        this.teamNo = teamNo;
    }

    public int getTeamMemberSum() {
        return teamMemberSum;
    }

    public void setTeamMemberSum(int teamMemberSum) {
        this.teamMemberSum = teamMemberSum;
    }

    public String getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDiff() {
        return diff;
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }

    public double getGrow() {
        return grow;
    }

    public void setGrow(double grow) {
        this.grow = grow;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

}
