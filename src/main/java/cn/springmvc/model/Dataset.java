package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class Dataset {
    private int datasetId;
    private String datasetName;
    private String homeLink;
    private String datasetProfile;
    private String description;
    private String lastUpdatedTime;
    private String datasetProvider;
    private String datasetSize;
    private int views;
    private int downloads;
    private int kernels;
    private int topics;
    private int votes;
    private String datasetType;

    public int getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(int datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
    }

    public String getDatasetProfile() {
        return datasetProfile;
    }

    public void setDatasetProfile(String datasetProfile) {
        this.datasetProfile = datasetProfile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getDatasetProvider() {
        return datasetProvider;
    }

    public void setDatasetProvider(String datasetProvider) {
        this.datasetProvider = datasetProvider;
    }

    public String getDatasetSize() {
        return datasetSize;
    }

    public void setDatasetSize(String datasetSize) {
        this.datasetSize = datasetSize;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getKernels() {
        return kernels;
    }

    public void setKernels(int kernels) {
        this.kernels = kernels;
    }

    public int getTopics() {
        return topics;
    }

    public void setTopics(int topics) {
        this.topics = topics;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }
}
