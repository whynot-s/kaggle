package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class DiscussionReply {
    private int discussionId;
    private String discussionName;
    private int discussionAuthorId;
    private String discussionAuthorName;
    private int commentNo;
    private int replyNo;
    private int replierId;
    private String replierName;
    private String replyText;
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

    public int getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(int replyNo) {
        this.replyNo = replyNo;
    }

    public int getReplierId() {
        return replierId;
    }

    public void setReplierId(int replierId) {
        this.replierId = replierId;
    }

    public String getReplierName() {
        return replierName;
    }

    public void setReplierName(String replierName) {
        this.replierName = replierName;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }
}
