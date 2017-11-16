package cn.springmvc.model;

public class CompetitorAbility {
    private int competitorId;
    private String competitorName;
    private Double image;
    private Double text;
    private Double tabular;
    private Double waft;
    private Double audio;
    private Double time_series;
    private Double graph;
    private Double adversarial_learning;
    private Double binary_classification;
    private Double forecasting;
    private Double multiclass_classification;
    private Double object_identification;
    private Double object_detection;
    private Double regression;
    private Double duplicate_detection;
    private Double artificial_intelligence;
    private Double object_segmentation;
    private Double object_labeling;
    private Double optimization;
    private Double ranking;
    private Double totalScore;

    public Double getScoreByTagName(String tagName){
        if (tagName.equals("image"))    return getImage();
        else if (tagName.equals("text"))  return getText();
        else if (tagName.equals("tabular")) return getTabular();
        else if (tagName.equals("waft"))  return getWaft();
        else if (tagName.equals("audio")) return getAudio();
        else if (tagName.equals("time series"))   return getTime_series();
        else if (tagName.equals("graph")) return getGraph();
        else if (tagName.equals("adversarial learning"))  return getAdversarial_learning();
        else if (tagName.equals("binary classification")) return getBinary_classification();
        else if (tagName.equals("forecasting"))   return getForecasting();
        else if (tagName.equals("multiclass classification")) return getMulticlass_classification();
        else if (tagName.equals("object identification")) return getObject_identification();
        else if (tagName.equals("object detection"))  return getObject_detection();
        else if (tagName.equals("regression"))    return getRegression();
        else if (tagName.equals("duplicate detection"))   return getDuplicate_detection();
        else if (tagName.equals("artificial intelligence"))   return getArtificial_intelligence();
        else if (tagName.equals("object segmentation"))   return getObject_segmentation();
        else if (tagName.equals("object labeling"))   return getObject_labeling();
        else if (tagName.equals("optimization"))  return getOptimization();
        else if (tagName.equals("ranking"))   return getRanking();
        else if (tagName.equals("totalScore"))    return getTotalScore();
        else{
            System.out.println("wrong tagName :"+tagName);
            return 0.0;
        }
    }

    public Double getObject_detection() {
        return object_detection;
    }

    public void setObject_detection(Double object_detection) {
        this.object_detection = object_detection;
    }

    public int getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(int competitorId) {
        this.competitorId = competitorId;
    }

    public String getCompetitorName() {
        return competitorName;
    }

    public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
    }

    public Double getImage() {
        return image;
    }

    public void setImage(Double image) {
        this.image = image;
    }

    public Double getText() {
        return text;
    }

    public void setText(Double text) {
        this.text = text;
    }

    public Double getTabular() {
        return tabular;
    }

    public void setTabular(Double tabular) {
        this.tabular = tabular;
    }

    public Double getWaft() {
        return waft;
    }

    public void setWaft(Double waft) {
        this.waft = waft;
    }

    public Double getAudio() {
        return audio;
    }

    public void setAudio(Double audio) {
        this.audio = audio;
    }

    public Double getTime_series() {
        return time_series;
    }

    public void setTime_series(Double time_series) {
        this.time_series = time_series;
    }

    public Double getGraph() {
        return graph;
    }

    public void setGraph(Double graph) {
        this.graph = graph;
    }

    public Double getAdversarial_learning() {
        return adversarial_learning;
    }

    public void setAdversarial_learning(Double adversarial_learning) {
        this.adversarial_learning = adversarial_learning;
    }

    public Double getBinary_classification() {
        return binary_classification;
    }

    public void setBinary_classification(Double binary_classification) {
        this.binary_classification = binary_classification;
    }

    public Double getForecasting() {
        return forecasting;
    }

    public void setForecasting(Double forecasting) {
        this.forecasting = forecasting;
    }

    public Double getMulticlass_classification() {
        return multiclass_classification;
    }

    public void setMulticlass_classification(Double multiclass_classification) {
        this.multiclass_classification = multiclass_classification;
    }

    public Double getObject_identification() {
        return object_identification;
    }

    public void setObject_identification(Double object_identification) {
        this.object_identification = object_identification;
    }

    public Double getRegression() {
        return regression;
    }

    public void setRegression(Double regression) {
        this.regression = regression;
    }

    public Double getDuplicate_detection() {
        return duplicate_detection;
    }

    public void setDuplicate_detection(Double duplicate_detection) {
        this.duplicate_detection = duplicate_detection;
    }

    public Double getArtificial_intelligence() {
        return artificial_intelligence;
    }

    public void setArtificial_intelligence(Double artificial_intelligence) {
        this.artificial_intelligence = artificial_intelligence;
    }

    public Double getObject_segmentation() {
        return object_segmentation;
    }

    public void setObject_segmentation(Double object_segmentation) {
        this.object_segmentation = object_segmentation;
    }

    public Double getObject_labeling() {
        return object_labeling;
    }

    public void setObject_labeling(Double object_labeling) {
        this.object_labeling = object_labeling;
    }

    public Double getOptimization() {
        return optimization;
    }

    public void setOptimization(Double optimization) {
        this.optimization = optimization;
    }

    public Double getRanking() {
        return ranking;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}