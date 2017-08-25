package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class KernelVote {
    private int kernelId;
    private String kernelName;
    private int kernelAuthorId;
    private String kernelAuthorName;
    private int voterId;
    private String voterName;

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
