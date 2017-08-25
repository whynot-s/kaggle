package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class DiscussionComment {
    private int discussionId;
    private String discussionName;
    private int discussionAuthorId;
    private String discussionAuthorName;
    private int commentNo;
    private int commenterId;
    private String commenterName;
    private String commentText;
    private int voteNum;

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

    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }

    public int getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(int commenterId) {
        this.commenterId = commenterId;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }
}
