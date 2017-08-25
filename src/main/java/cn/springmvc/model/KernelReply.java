package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class KernelReply {
    private int kernelId;
    private String kernelName;
    private int kernelAuthorId;
    private String kernelAuthorName;
    private int commentNo;
    private int replyNo;
    private int replierId;
    private String replierName;
    private String replyText;
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
