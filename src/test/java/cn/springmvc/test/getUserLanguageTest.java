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
    private CompetitorAbilityDao competitorAbilityDao;

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
        //competitiorRelation.socialCostGen();
        recomend1.experiment();
        //testDataGet.getCompetitorToTest();
        //testDataGet.getTeamToTestNumber();
        //competitorAbilityGen.scoreToOne();
       // testDataGet.getCompetitorToTest();

    }
}
