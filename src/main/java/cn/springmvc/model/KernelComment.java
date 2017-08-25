package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class KernelComment {
    private int kernelId;
    private String kernelName;
    private int kernelAuthorId;
    private String kernelAuthorName;
    private int commentNo;
    private int commenterId;
    private String commenterName;
    private String commentText;
    private int voteNum;

    public int getKernelId() {
        return kernelId;
    }

    public void setKernelId(int kernelId) {
        this.kernelId = kernelId;
    }

    public String getKernelName() {
        return kernelName;
    }

    public void setKernelName(String kernelName) {
        this.kernelName = kernelName;
    }

    public int getKernelAuthorId() {
        return kernelAuthorId;
    }

    public void setKernelAuthorId(int kernelAuthorId) {
        this.kernelAuthorId = kernelAuthorId;
    }

    public String getKernelAuthorName() {
        return kernelAuthorName;
    }

    public void setKernelAuthorName(String kernelAuthorName) {
        this.kernelAuthorName = kernelAuthorName;
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
