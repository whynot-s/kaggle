package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class DiscussionVote {
    private int discussionId;
    private String discussionName;
    private int discussionAuthorId;
    private String discussionAuthorName;
    private int voterId;
    private String voterName;

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }

    public String getDiscussionName() {
        return discussionName;
    }

    public void setDiscussionName(String discussionName) {
        this.discussionName = discussionName;
    }

    public int getDiscussionAuthorId() {
        return discussionAuthorId;
    }

    public void setDiscussionAuthorId(int discussionAuthorId) {
        this.discussionAuthorId = discussionAuthorId;
    }

    public String getDiscussionAuthorName() {
        return discussionAuthorName;
    }

    public void setDiscussionAuthorName(String discussionAuthorName) {
        this.discussionAuthorName = discussionAuthorName;
    }

    public int getVoterId() {
        return voterId;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }
}
