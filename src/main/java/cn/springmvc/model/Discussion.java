package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class Discussion {
    private int discussionId;
    private String discussionName;
    private String homeLink;
    private int discussionAuthorId;
    private String discussionAuthorName;
    private String sourceName;
    private String category;
    private int voters;
    private int commentsNum;
    private String discussionText;
    private int kernelOrNot;

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

    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getVoters() {
        return voters;
    }

    public void setVoters(int voters) {
        this.voters = voters;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public String getDiscussionText() {
        return discussionText;
    }

    public void setDiscussionText(String discussionText) {
        this.discussionText = discussionText;
    }

    public int getKernelOrNot() {
        return kernelOrNot;
    }

    public void setKernelOrNot(int kernelOrNot) {
        this.kernelOrNot = kernelOrNot;
    }
}
