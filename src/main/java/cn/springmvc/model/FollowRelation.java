package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class FollowRelation {
    private int competitorId;
    private int followerId;

    public int getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(int competitorId) {
        this.competitorId = competitorId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }
}
