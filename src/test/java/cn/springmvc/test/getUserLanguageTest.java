package cn.springmvc.test;


import cn.springmvc.dao.CompetitorAbilityDao;
import cn.springmvc.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/applicationContext.xml")
public class getUserLanguageTest {

    @Autowired
    private GetUserLanguage getUserLanguage;

    @Autowired
    private CompetitorRelation competitiorRelation;

    @Autowired
    private Recommend recommend;

    @Autowired
    private CompetitorIntimacy intimacy;

    @Autowired
    private CompetitorRecordAnalysis competitorRecordAnalysis;

    @Autowired
    private TeamFailed teamFailed;

    @Autowired
    private CompetitorAbilityGen competitorAbilityGen;

    @Autowired
    private Recomend1 recomend1;

    @Autowired
    private TestDataGet testDataGet;

    @Autowired
    private IntimacyContrastExperiment intimacyContrastExperiment;

    @Autowired
    private ResultAnalysis resultAnalysis;
    @Test
    public void main() {
        String[] tags = new String[]{"image", "tabular", "binary classification", "totalScore"};
        recommend.getRecommendTeam(3757, 2, tags, 0.1, 0.3, 1.0, 0.1,4.0);
    }

    @Test
    public void test() {
        //competitorRecordAnalysis.competitorAnalysis();
       //teamFailed.discussionMotivationAnalysis();
        //competitorAbilityGen.totalAbilityImprove();
       // competitorAbilityGen.totalAbilityGen();
        //competitiorRelation.costGen();
        //competitorRecordAnalysis.getCompetitorRecord();
        intimacyContrastExperiment.experiment_4();
        //competitiorRelation.socialCostGen();
        //recomend1.experiment();
        //testDataGet.getCompetitorToTest();
        //testDataGet.getTeamToTestNumber();
        //competitorAbilityGen.scoreToOne();
       // testDataGet.getCompetitorToTest();

    }

    @Test
    public void test2() {
        intimacyContrastExperiment.experiment_3();
    }

    @Test
    public void test3() {
        resultAnalysis.getPRate("recommendResult_2_test",2);
        //testDataGet.getCompetitorToTest();
    }
}
