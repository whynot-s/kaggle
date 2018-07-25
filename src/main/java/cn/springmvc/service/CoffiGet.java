package cn.springmvc.service;

import cn.springmvc.dao.CoffiTableDao;
import cn.springmvc.dao.CompetitionDao;
import cn.springmvc.dao.CompetitionLeaderboardDao;
import cn.springmvc.dao.TeamRecordAnalysisDao;
import cn.springmvc.model.TeamRecordAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by YLT on 2018/3/8.
 */
@Component
public class CoffiGet {
    @Autowired
    private CompetitionLeaderboardDao competitionLeaderboardDao;

    @Autowired
    private TeamCost teamCost;

    @Autowired
    private AbilityDiff abilityDiff;

    @Autowired
    private AbilityGrow abilityGrow;

    @Autowired
    private Recomend1 recomend1;

    @Autowired
    private TeamRecordAnalysisDao teamRecordAnalysisDao;

    @Autowired
    private CoffiTableDao coffiTableDao;

    @Autowired
    private TestDataGet testDataGet;

    @Autowired
    private TeamSuccessRate teamSuccessRate;

    /*
    teamRecordAnalysis
    * 历史参赛记录中（训练数据中），得到训练数据中团队参赛的记录，以及各队伍的亲密度、能力差异和能力成长
    * */
    public void getTeamRecordInTrain(){
        //DataPreLoad.dataLoad();
        int count = 0;
        ArrayList<String> allLeaderBoardRecord = competitionLeaderboardDao.getAllLeaderBoardRecord();
        HashMap<Integer,Integer> teamNumberMap = new HashMap<Integer, Integer>();

        for (String record:allLeaderBoardRecord) {
            boolean flag = false;
            count ++;
            //System.out.println( ++ count);
            String[] members = record.split("&");
            if (members.length < 2){
                continue;
            }

            int [] memberIds = new int[members.length];
            for (int i = 0; i < members.length; i ++){
                memberIds[i] = Integer.parseInt(members[i]);
                if (!DataPreLoad.competitorAbilityMap.containsKey(memberIds[i])){
                    flag = true;
                    break;
                }
            }
            if (flag == true){
                continue;
            }
            double[] memberAbility = teamSuccessRate.loadTeamAbility(memberIds,memberIds.length);
            int teamMemberSum = members.length;
            String teamMember = record;
            double cost = teamCost.getTeamCost(memberIds,teamMemberSum,0.7);
            double diff = abilityDiff.getAbilityDiff(memberAbility,teamMemberSum);
            double grow = abilityGrow.getGrowSpace(memberAbility,teamMemberSum);
            for (int member:memberIds){
                int teamNo;
                if (teamNumberMap.containsKey(member)){
                    teamNo = teamNumberMap.get(member) + 1;
                    teamNumberMap.put(member,teamNo);
                }else {
                    teamNumberMap.put(member,1);
                    teamNo = 1;
                }
                TeamRecordAnalysis teamRecord = new TeamRecordAnalysis();
                teamRecord.setMember1(member);
                teamRecord.setTeamNo(teamNo);
                teamRecord.setTeamMemberSum(teamMemberSum);
                teamRecord.setTeamMember(teamMember);
                teamRecord.setCost(cost);
                teamRecord.setDiff(diff);
                teamRecord.setGrow(grow);
                teamRecordAnalysisDao.insert(teamRecord);
            }
        }
    }

    /*
    * 参数获取方式一：原始简单统计方式
    * */
    public void getCoffiMethod1(){
        List<Integer> allTestCompetitors = teamRecordAnalysisDao.getAllTestCompetitors();
        double costAvg = teamRecordAnalysisDao.getAvgColumn("cost");
        double diffAvg = teamRecordAnalysisDao.getAvgColumn("diff");
        double growAvg = teamRecordAnalysisDao.getAvgColumn("grow");

        for (int testCompetitor:allTestCompetitors) {
            double costSum = teamRecordAnalysisDao.getAllColumnOfCompetitor("cost",testCompetitor,"TeamRecordAnalysis_test");
            double diffSum = teamRecordAnalysisDao.getAllColumnOfCompetitor("diff",testCompetitor,"TeamRecordAnalysis_test");
            double growSum = teamRecordAnalysisDao.getAllColumnOfCompetitor("grow",testCompetitor,"TeamRecordAnalysis_test");

            double costCoffi = costSum/(costSum/costAvg + diffSum/diffAvg + growSum/growAvg);
            double diffCoffi = diffSum/(costSum/costAvg + diffSum/diffAvg + growSum/growAvg);
            double growCoffi = growSum/(costSum/costAvg + diffSum/diffAvg + growSum/growAvg);

            coffiTableDao.insertCoffiTable(testCompetitor,costCoffi,diffCoffi,growCoffi);
        }
    }

    /*
   * 参数获取方式二：原始简单统计方式
   * */
    public void getCoffiMethod2(){
        List<Integer> allTestCompetitors = teamRecordAnalysisDao.getAllTestCompetitors();

        for (int testCompetitor:allTestCompetitors) {
            double costSum = teamRecordAnalysisDao.getAvgColumnOfCompetitor("cost",testCompetitor,"TeamRecordAnalysis_test");
            double diffSum = teamRecordAnalysisDao.getAvgColumnOfCompetitor("diff",testCompetitor,"TeamRecordAnalysis_test");
            double growSum = teamRecordAnalysisDao.getAvgColumnOfCompetitor("grow",testCompetitor,"TeamRecordAnalysis_test");

            double costCoffi = (1 - costSum)/(1 - costSum + 1 - diffSum + growSum);
            double diffCoffi = (1 - diffSum)/(1 - costSum + 1 - diffSum + growSum);
            double growCoffi = growSum/(1 - costSum + 1 - diffSum + growSum);

            coffiTableDao.insertCoffiTable(testCompetitor,costCoffi,diffCoffi,growCoffi);
        }
    }

    /*
   * 参数获取方式三：个性化定制方式，无约束求最优问题
   * */
    public void getCoffiMethod3(List<Integer> testSet){
        //List<Integer> testSet4 = testDataGet.getTestSet4();
        //List<Integer> testSet = teamRecordAnalysisDao.getAllTestCompetitors();

        for (int testCompetitor:testSet) {
            System.out.println(testCompetitor + "*****" );
            double costCoffi = 0;
            double diffCoffi = 0;
            double growCoffi = 0;
            int count = 0;
            if (teamRecordAnalysisDao.exist(testCompetitor) == 0){
                continue;
            }

            double costSum = teamRecordAnalysisDao.getAvgColumnOfCompetitor("cost",testCompetitor,"TeamRecordAnalysis");
            double diffSum = teamRecordAnalysisDao.getAvgColumnOfCompetitor("diff",testCompetitor,"TeamRecordAnalysis");
            double growSum = teamRecordAnalysisDao.getAvgColumnOfCompetitor("grow",testCompetitor,"TeamRecordAnalysis");

            if (1 - costSum < growSum && (1 - diffSum)/5 < growSum ){
                growCoffi = 1;
                count ++;
            }

            if (1 - costSum < growSum && (1 - diffSum)/5 > growSum){
                diffCoffi = 1;
                count ++;
            }

            if (1 - costSum > growSum && (1 - diffSum)/5 < growSum){
                costCoffi = 1;
                count ++;
            }

            if (1 - costSum > growSum && (1 - diffSum)/5 > growSum){
                if (1 - costSum >= (1 - diffSum)/5){
                    costCoffi = 1;
                    count ++;
                }else {
                    diffCoffi = 1;
                    count ++;
                }
            }
            System.out.println(testCompetitor + "*****" + count);
            coffiTableDao.insertCoffiTable(testCompetitor,costCoffi,diffCoffi,growCoffi);
        }
    }

    /*
    * 参数获取方式四：约束最优化问题，线性规划
    * matlab中
    * */
    public void getCoffiMethod4(List<Integer> testSet){

    }
}
