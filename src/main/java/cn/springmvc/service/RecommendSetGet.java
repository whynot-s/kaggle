package cn.springmvc.service;

import cn.springmvc.dao.CoffiTableDao;
import cn.springmvc.dao.CompetitorRecordDao;
import cn.springmvc.dao.TeamRecordAnalysisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by YLT on 2018/3/22.
 */
@Component
public class RecommendSetGet {

    @Autowired
    private CompetitorRecordDao competitorRecordDao;

    @Autowired
    private TeamRecordAnalysisDao teamRecordAnalysisDao;

    @Autowired
    private CoffiTableDao coffiTableDao;
    /*
    * 过滤出与被推荐者关系极不亲密、且能力差很大的用户
    * 参数一为被推荐者的id，参数二为能力差异阈值
    * */

    /*
    * 推荐集合为：在测试数据集或者训练数据集中曾团队参赛的人
    * 过滤出能力差大于diff的人
    * */
    public List<Integer> getRecommendSet0(int competitorId, double abilityDiff, String tend) {
        //List<CoffiTable> allCompetitors = coffiTableDao.getTestCompetitors();
        /*List<Integer> allCompetitorsId = new ArrayList<Integer>();
        for (CoffiTable competitor:allCompetitors){
            if (tend.equals("cost") && competitor.getCostPercent() == 1){
                allCompetitorsId.add(competitor.getMember1());
            }
            if (tend.equals("diff") && competitor.getDiffPercent() == 1){
                allCompetitorsId.add(competitor.getMember1());
            }
            if (tend.equals("grow") && competitor.getGrowPercent() == 1){
                allCompetitorsId.add(competitor.getMember1());
            }
        }*/

        Set<Integer> allCompetitorsSet = DataPreLoad.competitorAbilityMap.keySet();

        List<Integer> set1 = competitorRecordDao.getTeamCompetitor("competitorrecord_test");
        List<Integer> set2 = competitorRecordDao.getTeamCompetitor("competitorrecord_train");
        List<Integer> allCompetitors  = new ArrayList<Integer>();
        for (int competitor:set1){
            if (allCompetitors.contains(competitor) || !allCompetitorsSet.contains(competitor)){
                continue;
            }else {
                allCompetitors.add(competitor);
            }
        }
        for (int competitor:set2){
            if (allCompetitors.contains(competitor) || !allCompetitorsSet.contains(competitor)){
                continue;
            }else {
                allCompetitors.add(competitor);
            }
        }
        if (allCompetitors.contains(competitorId)){
            allCompetitors.remove(allCompetitors.indexOf(competitorId));
        }

        for (int i = 0; i < allCompetitors.size();){
            double diff = DataPreLoad.competitorAbilityMap.get(competitorId) - DataPreLoad.competitorAbilityMap.get(allCompetitors.get(i));
            if (Math.abs(diff) > abilityDiff){
                allCompetitors.remove(i);
            }else {
                i++;
            }
        }
        return allCompetitors;
    }

    /*
    * 推荐集合为：有能力评价的人的全集
    * 过滤出能力差异大于阈值、且亲密度很低的人
    * */
    public List<Integer> getRecommendSet1(int competitorId, double abilityDiff) {
        Set<Integer> allCompetitorsSet = DataPreLoad.competitorAbilityMap.keySet();
        List<Integer> allCompetitors  = new ArrayList<Integer>();
        for (Integer competitor: allCompetitorsSet){
            allCompetitors.add(competitor);
        }
        allCompetitors.remove(allCompetitors.indexOf(competitorId));

        for (int i = 0; i < allCompetitors.size(); ) {
            double diff = DataPreLoad.competitorAbilityMap.get(competitorId) - DataPreLoad.competitorAbilityMap.get(allCompetitors.get(i));
            if (DataPreLoad.relationMap.get(competitorId) != null){
                if (Math.abs(diff) > abilityDiff && !DataPreLoad.relationMap.get(competitorId).containsKey(allCompetitors.get(i))){
                    allCompetitors.remove(i);
                }else {
                    i++;
                }
            }else{
                if (Math.abs(diff) > abilityDiff){
                    allCompetitors.remove(i);
                }else {
                    i++;
                }
            }
        }

        return allCompetitors;
    }

    /*
    * 推荐集合为：有能力评价的人的全集
    * */
    public List<Integer> getRecommendSet2(int competitorId, double abilityDiff) {
        Set<Integer> allCompetitorsSet = DataPreLoad.competitorAbilityMap.keySet();
        List<Integer> allCompetitors  = new ArrayList<Integer>();
        for (Integer competitor: allCompetitorsSet){
            allCompetitors.add(competitor);
        }
        allCompetitors.remove(allCompetitors.indexOf(competitorId));

        return allCompetitors;
    }

    /*
    * 推荐集合为：有能力评价的人的全集,方法2
    * */
    public List<Integer> getRecommendSet3(int competitorId, double abilityDiff) {
        Set<Integer> allCompetitorsSet = DataPreLoad.competitorAbilityMap.keySet();
        List<Integer> allCompetitors  = new ArrayList<Integer>();

        for (Integer competitor: allCompetitorsSet){
            if(competitorId == competitor.intValue())
                continue;
            allCompetitors.add(competitor);
        }

        return allCompetitors;
    }

    /*
    全部数据中
    * 推荐集合为：存在组队意愿的人，有过团队参赛经历 大于 1次
    * */
    public List<Integer> getRecommendSet4() {
        List<Integer> allCompetitors  = teamRecordAnalysisDao.getCompetitorsTeamMoreThan();
        System.out.println(allCompetitors.size());
        return allCompetitors;
    }

    /*
    * 推荐集合为：测试数据集中团队参赛过、并有能力评价的人
    * */
    public List<Integer> getRecommendSet5(){
        Set<Integer> allCompetitorsSet = DataPreLoad.competitorAbilityMap.keySet();
        List<Integer> allCompetitors = competitorRecordDao.getTeamCompetitor("competitorrecord_test");
        for (int i = 0; i < allCompetitors.size();){
            if (allCompetitorsSet.contains(allCompetitors.get(i))){
                i++;
            }else {
                allCompetitors.remove(i);
            }
        }
        return allCompetitors;
    }

    public List<Integer> getRecommendSet6(){
        List<Integer> allCompetitorsSet = coffiTableDao.getCoffiMemberIds("coffiTable_end2_0_6");
        return allCompetitorsSet;
    }
}
