package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class Kernel {
    private int kernelId;
    private String kernelName;
    private String homeLink;
    private String languages;
    private int kernelAuthorId;
    private String kernelAuthorName;
    private String postDate;
    private String datasetName;
    private String datasetSource;
    private int votersNum;
    private int commentsNum;
    private int versionNum;
    private int forksNum;
    private String kernelCode;

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

    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
    }

    public String getLanguage() {
        return languages;
    }

    public void setLanguage(String language) {
        this.languages = language;
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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getDatasetSource() {
        return datasetSource;
    }

    public void setDatasetSource(String datasetSource) {
        this.datasetSource = datasetSource;
    }

    public int getVotersNum() {
        return votersNum;
    }

    public void setVotersNum(int votersNum) {
        this.votersNum = votersNum;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public int getForksNum() {
        return forksNum;
    }

    public void setForksNum(int forksNum) {
        this.forksNum = forksNum;
    }

    public String getKernelCode() {
        return kernelCode;
    }

    public void setKernelCode(String kernelCode) {
        this.kernelCode = kernelCode;
    }
}
