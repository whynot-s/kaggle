package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class KernelFork {
    private int kernelId;
    private String kernelName;
    private int kernelAuthorId;
    private String kernelAuthorName;
    private int forkId;
    private String forkName;

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

    public int getForkId() {
        return forkId;
    }

    public void setForkId(int forkId) {
        this.forkId = forkId;
    }

    public String getForkName() {
        return forkName;
    }

    public void setForkName(String forkName) {
        this.forkName = forkName;
    }
}
