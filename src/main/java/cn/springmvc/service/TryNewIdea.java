package cn.springmvc.service;

import cn.springmvc.dao.CoffiTableDao;
import cn.springmvc.dao.TeamRecordAnalysisDao;
import cn.springmvc.model.CoffiTable;
import cn.springmvc.model.TeamRecordAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YLT on 2018/4/6.
 */
@Service
public class TryNewIdea {
    @Autowired
    private CoffiTableDao coffiTableDao;


    @Autowired
    private Recomend1 recomend1;

    @Autowired
    private IntimacyContrastExperiment intimacyContrastExperiment;

    @Autowired
    private TeamRecordAnalysisDao teamRecordAnalysisDao;

    @Autowired
    private TeamCost teamCost;

    @Autowired
    private AbilityDiff abilityDiff;

    @Autowired
    private AbilityGrow abilityGrow;

    @Autowired
    private TeamSuccessRate teamSuccessRate;


    public void method1(){
        List<Integer> largeSet = coffiTableDao.getCoffiMemberIds("coffiTable_testSet5_liner_1_1");
        List<Integer> smallSet = coffiTableDao.getCoffiMemberIds("coffiTable_testSet5_liner_1_2");
        for (int i:smallSet
             ) {
            if (largeSet.contains(i)){
                largeSet.remove(largeSet.indexOf(i));
            }
        }
        System.out.println(largeSet.size()+":");
        for (int i : largeSet){
            CoffiTable coffi = coffiTableDao.getCoffiByCompetitorId("coffiTable_testSet5_liner_0_0",i);
            recomend1.recommendForCompetitorWithCloseness(coffi.getMember1(),2,0.3,5.6*coffi.getCostPercent(),1.2*coffi.getDiffPercent(),6.3*coffi.getGrowPercent(),0.7);
            //intimacyContrastExperiment.compare(coffi.getMember1(),2,0.7,5.6*coffi.getCostPercent(),1.2*coffi.getDiffPercent(),6.3*coffi.getGrowPercent());
        }
    }

    public void updateTeamRecordAnalysis(){
        List<TeamRecordAnalysis> allTeamRecord = teamRecordAnalysisDao.getAllTeamRecord("TeamRecordAnalysisAll");

        for (TeamRecordAnalysis teamRecord:allTeamRecord) {
            int competitorId = teamRecord.getMember1();
            int index = -1;
            String memberIdStr = teamRecord.getTeamMember();

            String[] membersStr = memberIdStr.split("&");
            int teamSize = membersStr.length;
            int [] membersId = new int[teamSize];
            for (int i = 0; i < teamSize ; i ++) {
                if (membersStr[i].equals(String.valueOf(competitorId))){
                    index = i;
                }
                membersId[i] = Integer.parseInt(membersStr[i]);
            }

            //double cost = teamCost.getTeamCost2(membersId,teamSize,0.7,index);
            double[] memberAbility = teamSuccessRate.loadTeamAbility(membersId,teamSize);
            double diff = abilityDiff.getAbilityDiff6(memberAbility,teamSize,index);
            double grow = abilityGrow.getGrowSpace2(memberAbility,teamSize,index);
            teamRecordAnalysisDao.updateFeature(teamRecord.getCost(),diff,grow,competitorId,teamRecord.getTeamNo());
        }
    }


    public void method2(){

    }
}
