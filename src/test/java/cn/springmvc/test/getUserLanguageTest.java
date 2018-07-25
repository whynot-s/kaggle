package cn.springmvc.test;


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

    @Autowired
    private CompetitorIntimacy competitorIntimacy;

    @Autowired
    private DataPreHandle dataPreHandle;

    @Autowired
    private DataPreLoad dataPreLoad;

    @Autowired
    private Experiment experiment;

    @Autowired
    private CoffiGet coffiGet;

    @Autowired
    private TeamAnalysis teamAnalysis;

    @Autowired
    private RecommendSetGet recommendSetGet;

    @Autowired
    private TryNewIdea tryNewIdea;

    @Autowired
    private EndExperiment endExperiment;

    @Autowired
    private TraverseForBest traverseForBest;

    @Autowired
    private TeamSuccessRate teamSuccessRate;

    @Autowired
    private VarianceGet varianceGet;
/*
    @Autowired
    private KaggleWeb kaggleWeb;*/



    @Test
    public void getResult(){
        KaggleWeb kaggleWeb = new KaggleWeb();
        kaggleWeb.recommend("Santi","",2,"");
        //*resultAnalysis.getPRate("recommendResult_2",2);
        //dataPreLoad.dataLoad();
       // tryNewIdea.method1();*//*
        //testDataGet.getTestSet2();
        //resultAnalysis.getPRate("recommendResult_3",3);
        //System.out.println(kaggleWeb.recommendationWeb(543,"",2,""));
    }
   /* @Test
    public void test() {
       // coffiGet.getCoffiMethod3(testDataGet.getTestSet5());
        //teamAnalysis.teamFeatureAnalysis();
        dataPreLoad.dataLoad();
        experiment.exp2();
        //testDataGet.getTestSet6();
        //coffiGet.getTeamRecordInTrain();
        //resultAnalysis.getPRate("recommendResult_2",2);
        //dataPreHandle.relationTableDelete();
        //dataPreLoad.dataLoad();
        //coffiGet.getTeamRecordInTrain();
       // coffiGet.getCoffiMethod3();
        //testDataGet.getTestSetTeamRecord(testDataGet.getTestSet4());
        //intimacyContrastExperiment.experimentWithCoffi();
       *//* double[][] weight ={{0.0, 0.0, 0.1, 0.2, 0.4},
                            {0.0, 0.0, 0.0, 0.3, 0.1},
                            {0.1, 0.0, 0.0, 0.1, 0.5},
                            {0.2, 0.3, 0.1, 0.0, 0.5},
                            {0.4, 0.1, 0.5, 0.5, 0.0}};
        double [][] cal = new double[5][5];

        competitorIntimacy.calcTeamIntimacy(weight,cal);
        for (int i = 0; i < cal.length;i ++){
            for (int j =0 ;j < cal[0].length;j++){
                System.out.print(cal[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println(competitorIntimacy.calcTeamTotalIntimacy(cal));*//*
    }
    @Test
    public void test4() {
        competitorRecordAnalysis.getPercentOfTeam(10);
    }

    @Test
    public void test5() {
        dataPreLoad.dataLoad();
        dataPreLoad.coffiMapLoad();
        varianceGet.getVariance("recommendResult_2_random",2,0.7);
        //endExperiment.experiment9Random();
        //endExperiment.experiment6Best();
        //resultAnalysis.getPRate("recommendResult_2",2);
    }
    @Test
    public void test6() {
        dataPreLoad.dataLoad();
        dataPreLoad.coffiMapLoad();
        endExperiment.experiment9();
       // endExperiment.experiment9ContrastGrow();
        //double tempSuccessRate = teamSuccessRate.getTeamSuccessRateMutual2(new int[]{543,763,784}, 3, 0.7);
        //traverseForBest.recommendBestSituation3Mutual(543,3,0.7);
    }
    @Test
    public void test7() {
        competitorAbilityGen.competitorHandle2();
    }*/
}
