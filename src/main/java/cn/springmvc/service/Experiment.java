package cn.springmvc.service;


import cn.springmvc.dao.CoffiTableDao;
import cn.springmvc.model.CoffiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by YLT on 2018/3/9.
 */
@Component
public class Experiment {
    @Autowired
    private TestDataGet testDataGet;
    @Autowired
    private Recomend1 recomend1;
    @Autowired
    private CoffiTableDao coffiTableDao;
    @Autowired
    private IntimacyContrastExperiment intimacyContrastExperiment;
    @Autowired
    private TraverseForBest traverseForBest;

    /*
    * 修改推荐候选集
    * */
    public void exp1(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_1_70");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            intimacyContrastExperiment.compare(member1,2,0.7,4*costCoffi,diffCoffi,11*growCoffi);
        }
    }

    public void exp2(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_1_70");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            recomend1.recommendForCompetitor(member1, 2, 0.3, 4*costCoffi, diffCoffi,11*growCoffi, 0.7);
        }
    }

    public void exp3(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_1");
        //long begin = System.currentTimeMillis();
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy1",member1, 3, 4*costCoffi, diffCoffi,11*growCoffi, 0.7);
        }
        /*long timeCost =  (System.currentTimeMillis()-begin);
        System.out.println("recommend cost " + timeCost + "ms, average cost " + (timeCost/allTestCompetitors.size()) + "ms");*/
    }

    public void exp4(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_2");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy2",member1, 3, 4 * costCoffi, diffCoffi, 11 * growCoffi, 0.7);
        }
    }

    public void exp5(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_3");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy3",member1, 3, 4 * costCoffi, diffCoffi, 11 * growCoffi, 0.7);
        }
    }
    public void exp6(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_4");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy4",member1, 3, 4 * costCoffi, diffCoffi, 11 * growCoffi, 0.7);
        }
    }
    public void exp7(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_5");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy5",member1, 3, 4 * costCoffi, diffCoffi, 11 * growCoffi, 0.7);
        }
    }
    public void exp8(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_6");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy6",member1, 3, 4 * costCoffi, diffCoffi, 11 * growCoffi, 0.7);
        }
    }
    public void exp9(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_7");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy7",member1, 3, 4 * costCoffi, diffCoffi, 11 * growCoffi, 0.7);
        }
    }
    public void exp10(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_925");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy11",member1, 3, 4 * costCoffi, diffCoffi, 11 * growCoffi, 0.7);
        }
    }
    public void exp11(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_95");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy12",member1, 3, 4 * costCoffi, diffCoffi, 11 * growCoffi, 0.7);
        }
    }
    public void exp12(){
        List<CoffiTable> allTestCompetitors = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_0_975");
        for (CoffiTable testCompetitor: allTestCompetitors) {
            int member1 = testCompetitor.getMember1();
            double costCoffi = testCompetitor.getCostPercent();
            double diffCoffi = testCompetitor.getDiffPercent();
            double growCoffi = testCompetitor.getGrowPercent();
            traverseForBest.exp("recommendResult_3_best_copy13",member1, 3, 4 * costCoffi, diffCoffi, 11 * growCoffi, 0.7);
        }
    }

    public List<Integer> getCoffiDiff(String tableName1, String tableName2){
        List<Integer> largeSet = coffiTableDao.getCoffiMemberIds(tableName1);
        List<Integer> smallSet = coffiTableDao.getCoffiMemberIds(tableName2);
        for (int i:smallSet) {
            if (largeSet.contains(i)){
                largeSet.remove(largeSet.indexOf(i));
            }
        }
        for (int i : largeSet){
            CoffiTable coffi = coffiTableDao.getCoffiByCompetitorId(tableName1,i);
            coffiTableDao.insertCoffiTable(coffi.getMember1(),coffi.getCostPercent(),coffi.getDiffPercent(),coffi.getGrowPercent());
            //recomend1.recommendForCompetitorWithCloseness(coffi.getMember1(),2,0.3,4.1*coffi.getCostPercent(),1.1*coffi.getDiffPercent(),12.1*coffi.getGrowPercent(),0.7);
            //intimacyContrastExperiment.compare(coffi.getMember1(),2,0.7,4.1*coffi.getCostPercent(),1.1*coffi.getDiffPercent(),12.1*coffi.getGrowPercent());
        }
        return largeSet;
    }
    public void getCoffi(){
        getCoffiDiff("coffiTable_testSet6_liner_0_0","coffiTable_testSet6_liner_1_0");
        getCoffiDiff("coffiTable_testSet6_liner_1_0","coffiTable_testSet6_liner_1_05");
        getCoffiDiff("coffiTable_testSet6_liner_1_05","coffiTable_testSet6_liner_1_1");
        getCoffiDiff("coffiTable_testSet6_liner_1_1","coffiTable_testSet6_liner_1_2");
        getCoffiDiff("coffiTable_testSet6_liner_1_2","coffiTable_testSet6_liner_1_4");
        getCoffiDiff("coffiTable_testSet6_liner_1_4","coffiTable_testSet6_liner_1_5");
        getCoffiDiff("coffiTable_testSet6_liner_1_5","coffiTable_testSet6_liner_1_6");
        getCoffiDiff("coffiTable_testSet6_liner_1_6","coffiTable_testSet6_liner_2_0");
        getCoffiDiff("coffiTable_testSet6_liner_2_0","coffiTable_testSet6_liner_3_0");
        getCoffiDiff("coffiTable_testSet6_liner_3_0","coffiTable_testSet6_liner_4_0");
        List<CoffiTable> lastCoffis = coffiTableDao.getTestCompetitors("coffiTable_testSet6_liner_4_0");
        for (CoffiTable coffi: lastCoffis){
            coffiTableDao.insertCoffiTable(coffi.getMember1(),coffi.getCostPercent(),coffi.getDiffPercent(),coffi.getGrowPercent());
        }
    }

    public void getCoffi2(){
        getCoffiDiff("coffiTable_testSet6_mutual_0_0","coffiTable_testSet6_mutual_0_5");
        getCoffiDiff("coffiTable_testSet6_mutual_0_5","coffiTable_testSet6_mutual_0_8");
        getCoffiDiff("coffiTable_testSet6_mutual_0_8","coffiTable_testSet6_mutual_0_9");
        getCoffiDiff("coffiTable_testSet6_mutual_0_9","coffiTable_testSet6_mutual_0_95");
        getCoffiDiff("coffiTable_testSet6_mutual_0_95","coffiTable_testSet6_mutual_1_0");
        getCoffiDiff("coffiTable_testSet6_mutual_1_0","coffiTable_testSet6_mutual_1_1");
        getCoffiDiff("coffiTable_testSet6_mutual_1_1","coffiTable_testSet6_mutual_1_2");
        getCoffiDiff("coffiTable_testSet6_mutual_1_2","coffiTable_testSet6_mutual_1_4");
        getCoffiDiff("coffiTable_testSet6_mutual_1_4","coffiTable_testSet6_mutual_1_5");
        getCoffiDiff("coffiTable_testSet6_mutual_1_5","coffiTable_testSet6_mutual_1_6");
        getCoffiDiff("coffiTable_testSet6_mutual_1_6","coffiTable_testSet6_mutual_1_8");
        getCoffiDiff("coffiTable_testSet6_mutual_1_8","coffiTable_testSet6_mutual_2_0");
        getCoffiDiff("coffiTable_testSet6_mutual_2_0","coffiTable_testSet6_mutual_2_5");
        /*getCoffiDiff("coffiTable_testSet6_mutual_2_5","coffiTable_testSet6_mutual_3_0");
        getCoffiDiff("coffiTable_testSet6_mutual_3_0","coffiTable_testSet6_mutual_4_0");*/
        List<CoffiTable> lastCoffis = coffiTableDao.getTestCompetitors("coffiTable_testSet6_mutual_2_5");
        for (CoffiTable coffi: lastCoffis){
            coffiTableDao.insertCoffiTable(coffi.getMember1(),coffi.getCostPercent(),coffi.getDiffPercent(),coffi.getGrowPercent());
        }
    }

    public void exp13(){
        List<Integer> testSet = testDataGet.getTestSet2();
        for (int i : testSet){
            CoffiTable coffi = DataPreLoad.coffiMap.get(i);
            if (coffi!=null){
               // recomend1.recommendForCompetitorWithCloseness(coffi.getMember1(),2,0.3,4.1*coffi.getCostPercent(),1.1*coffi.getDiffPercent(),12.1*coffi.getGrowPercent(),0.7);
                //intimacyContrastExperiment.compare(coffi.getMember1(),2,0.7,4.1*coffi.getCostPercent(),1.1*coffi.getDiffPercent(),12.1*coffi.getGrowPercent());
                recomend1.recommendForCompetitorMutual(i,2,0.7);
            }
        }
    }
}
