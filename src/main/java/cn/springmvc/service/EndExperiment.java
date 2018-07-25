package cn.springmvc.service;

import cn.springmvc.dao.CoffiTableDao;
import cn.springmvc.model.CoffiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


//最终实验开始
//在最后位置

/**
 * Created by YLT on 2018/4/9.
 */
@Component
public class EndExperiment {
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

    @Autowired
    private  ContrastExperiment contrastExperiment;

    public void expriment1(){

    }


    /*
    * 修改cost计算方式、修改建模方式
    * 选取测试集中 团队参赛过的用户；其参赛记录中的队友存在能力评价；其队友有过合作历史--》236个用户
    * 推荐集合：与其有过合作历史的用户
    * */
    public void experiment5(){

    }


    public void contrastExperiment(){

    }



    /*
   * 修改cost计算方式、修改建模方式
   * 给254个人（原267个人）做推荐：在历史数据中团队参赛次数大于等于2，且在测试数据中团队参赛过的人
   * 推荐集合为：历史数据中团队参赛次数大于等于2的用户集合--》3635
   * */
    public void experiment4(){
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
        }
        return largeSet;
    }

    public void getCoffi4(){
        getCoffiDiff("coffiTable_exp4_mutual_0_0","coffiTable_exp4_mutual_0_1");
        getCoffiDiff("coffiTable_exp4_mutual_0_1","coffiTable_exp4_mutual_0_2");
        getCoffiDiff("coffiTable_exp4_mutual_0_2","coffiTable_exp4_mutual_0_4");
        getCoffiDiff("coffiTable_exp4_mutual_0_4","coffiTable_exp4_mutual_0_45");
        getCoffiDiff("coffiTable_exp4_mutual_0_45","coffiTable_exp4_mutual_0_5");
        getCoffiDiff("coffiTable_exp4_mutual_0_5","coffiTable_exp4_mutual_0_55");
        getCoffiDiff("coffiTable_exp4_mutual_0_55","coffiTable_exp4_mutual_0_6");
        getCoffiDiff("coffiTable_exp4_mutual_0_6","coffiTable_exp4_mutual_0_7");
        getCoffiDiff("coffiTable_exp4_mutual_0_7","coffiTable_exp4_mutual_0_75");
        getCoffiDiff("coffiTable_exp4_mutual_0_75","coffiTable_exp4_mutual_0_8");
        getCoffiDiff("coffiTable_exp4_mutual_0_8","coffiTable_exp4_mutual_1_0");
        List<CoffiTable> lastCoffis = coffiTableDao.getTestCompetitors("coffiTable_exp4_mutual_1_0");
        for (CoffiTable coffi: lastCoffis){
            coffiTableDao.insertCoffiTable(coffi.getMember1(),coffi.getCostPercent(),coffi.getDiffPercent(),coffi.getGrowPercent());
        }
    }


    public void experiment6(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end_0_2");
        for (int coffi:allCoffi) {
            recomend1.recommendForCompetitorMutual2(coffi,4,0.7);
        }
    }

    public void experiment6Contrast(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end_0_2");
        for (int coffi:allCoffi){
            intimacyContrastExperiment.compareMutual(coffi,4,0.7);
        }
    }

    public void experiment6Best(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end_0_45");
        for (int coffi:allCoffi){
            traverseForBest.recommendBestSituation3Mutual(coffi,2,0.7);
        }
    }

    public void experiment7(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end_0_45");
        for (int coffi:allCoffi) {
            recomend1.recommendForCompetitorMutual3(coffi,3,0.7);
        }
    }

    public void experiment7Contrast(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end_0_45");
        for (int coffi:allCoffi) {
            intimacyContrastExperiment.compareMutual3(coffi,3,0.7);
        }
    }

    public void experiment8(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end_0_2");
        for (int coffi:allCoffi){
            recomend1.recommendForCompetitorMutual2(coffi,4,0.7);
        }
    }

    //最后实验中用到
    public void experiment9(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end2_0_6");
        for (int coffi:allCoffi){
            recomend1.recommendForCompetitorMutual2(coffi,2,0.7);
        }
    }

    public void experiment9Contrast(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end2_0_6");
        for (int coffi:allCoffi){
            intimacyContrastExperiment.compareMutual(coffi,4,0.7);
        }
    }

    public void experiment9Best(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end2_0_6");
        for (int coffi:allCoffi){
            traverseForBest.recommendBestSituation3Mutual(coffi,3,0.7);
        }
    }

    public void experiment9ContrastDiff(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end2_0_6");
        for (int coffi:allCoffi){
            contrastExperiment.diffCompare(coffi,4,0.7);
        }
    }

    public void experiment9ContrastGrow(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end2_0_6");
        for (int coffi:allCoffi){
            contrastExperiment.growCompare(coffi,2,0.7);
        }
    }

    public void experiment9Random(){
        List<Integer> allCoffi = coffiTableDao.getCoffiMemberIds("coffiTable_end2_0_6");
        for (int coffi:allCoffi){
            contrastExperiment.randomCompare(coffi,4,0.7);
        }
    }
}
