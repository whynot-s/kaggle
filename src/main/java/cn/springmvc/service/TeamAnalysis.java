package cn.springmvc.service;

import cn.springmvc.dao.CoffiTableDao;
import cn.springmvc.dao.CompetitorRecordDao;
import cn.springmvc.dao.TeamRecordAnalysisDao;
import cn.springmvc.model.CoffiTable;
import cn.springmvc.model.CompetitorRecord;
import cn.springmvc.model.TeamRecordAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by YLT on 2018/3/12.
 */
@Component
public class TeamAnalysis {
    @Autowired
    private TeamRecordAnalysisDao teamRecordAnalysisDao;

    @Autowired
    private CompetitorRecordDao competitorRecordDao;

    @Autowired
    private CoffiTableDao coffiTableDao;

    public void teamFeatureAnalysis(){
        List<TeamRecordAnalysis> teamRecordInTest = teamRecordAnalysisDao.getAllTeamRecord("TeamRecord_in_testData");
        for (TeamRecordAnalysis teamRecord:teamRecordInTest) {
            int member1 = teamRecord.getMember1();
            String[] members = teamRecord.getTeamMember().split("&");

            String times = "";
            List<CompetitorRecord> records = competitorRecordDao.getCompetitorRecordByCompetitorId(member1,"competitorrecord_train");
            HashMap<Integer,Integer> coworkers = new HashMap<Integer, Integer>();

            for (CompetitorRecord record : records) {
                if (record.getTeamOrNot() == 0){
                    continue;
                }
                List<Integer> competitors = competitorRecordDao.getLeaderBoardRecordMember(record.getCompetitionId(),record.getRanking());
                for (int competitor:competitors) {
                    if (competitor == member1){
                        continue;
                    }
                    if (coworkers.containsKey(competitor)){
                        coworkers.put(competitor,coworkers.get(competitor) + 1);
                    }else {
                        coworkers.put(competitor,1);
                    }
                }
            }
            for (String member: members){
                if (member.equals( member1)){
                    continue;
                }
                if (coworkers.containsKey(Integer.parseInt(member))) {
                    teamRecordAnalysisDao.updateColumnString("toAdd", member1, "Y");
                }
            }
           // teamRecordAnalysisDao.updateColumnString("coWorker",member1,coworkers.toString());

            //更新memberBefore字段
            /*for (String member: members) {
                if (Integer.parseInt(member) == member1) {
                    continue;
                }
                times += String.valueOf(teamBeforeOrNot(member1,Integer.parseInt(member),records)) + "&";
            }
            teamRecordAnalysisDao.updateColumnString("memberBefore",member1, times);*/

            //更新totalCompetition和totalTeamCompetition
            /*int totalCompetition = records.size();
            int totalTeamCompetition = 0;
            for (CompetitorRecord record:records) {
                if (record.getTeamOrNot() == 1){
                    totalTeamCompetition ++;
                }
            }
            teamRecordAnalysisDao.updateColumnString("totalCompetition",member1, String.valueOf(totalCompetition));
            teamRecordAnalysisDao.updateColumnString("totalTeamCompetition",member1, String.valueOf(totalTeamCompetition));*/


        }

    }

    public int teamBeforeOrNot(int competitor1, int competitor2, List<CompetitorRecord> records){
        int count = 0;
            for (CompetitorRecord record:records) {
                if(competitorRecordDao.exist("competitorrecord_train",record.getCompetitionId(),competitor2,record.getRanking()) >= 1){
                    count ++;
                }
            }
        return count;
    }

    public void getTrueTeamSuccessRate(){
        List<TeamRecordAnalysis> allTeamRecord = teamRecordAnalysisDao.getAllTeamRecord("TeamRecordAnalysisAll");
        for (TeamRecordAnalysis teamRecord:allTeamRecord){
            int competitor = teamRecord.getMember1();
            CoffiTable coffi = coffiTableDao.getCoffiByCompetitorId("coffiTable_testSet6_liner_0_8",competitor);
            if (coffi!=null) {
                double successRate = coffi.getCostPercent() * 4 * teamRecord.getCost() + coffi.getDiffPercent() * teamRecord.getDiff() + coffi.getGrowPercent() * 11 * teamRecord.getGrow();
                teamRecordAnalysisDao.updateSuccessRate(competitor, successRate);
            }
        }
    }
}
