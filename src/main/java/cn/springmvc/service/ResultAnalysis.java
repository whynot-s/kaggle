package cn.springmvc.service;

import cn.springmvc.dao.CompetitorRecordDao;
import cn.springmvc.dao.RecommendResultDao;
import cn.springmvc.model.CompetitorRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by YLT on 2018/1/15.
 */
@Service
public class ResultAnalysis {
    @Autowired
    private RecommendResultDao recommendResultDao;

    @Autowired
    private CompetitorRecordDao competitorRecordDao;

    @Autowired
    private AbilityDiff abilityDiff;

    @Autowired AbilityGrow abilityGrow;

    @Autowired
    private Recomend1 recomend1;

    @Autowired
    private TeamSuccessRate teamSuccessRate;

    public void getPRate(String tableName, int teamSize){
        List<HashMap<String, Object>> recommendResult = recommendResultDao.getRecommendResult(tableName);
        int allNum = recommendResult.size();
        int count = 0;
        boolean flag = false;

        for (HashMap<String,Object> result : recommendResult) {
            int competitor = Integer.parseInt(result.get("member1").toString());
            List<CompetitorRecord> records = competitorRecordDao.getCompetitorRecordByCompetitorId(competitor,"competitorrecord_test");
            for (int i = 2; i < teamSize + 1; i ++){
                String key = "member" + i;
                int member = Integer.parseInt(result.get(key).toString());
                for (CompetitorRecord record:records) {
                    if(competitorRecordDao.exist("competitorrecord_test",record.getCompetitionId(),member,record.getRanking()) >= 1){
                        recommendResultDao.setRightOrNot(tableName,competitor);
                        count ++;
                        flag = true;
                        break;
                    }
                }
                if (flag == true){
                    flag = false;
                    break;
                }
            }
        }
        System.out.println(count + "    " + allNum);
        System.out.println((double)(count)/allNum);
    }

    public void getDiff(){

    }

    public void updateSucessRate(String tableName,double p,double q){
        List<HashMap<String ,Object>> results = recommendResultDao.getRecommendResult(tableName);

        for (HashMap<String, Object> result:results) {
            double cost = Double.parseDouble(result.get("cost").toString());
            double diff = Double.parseDouble(result.get("diff").toString());
            double grow = Double.parseDouble(result.get("grow").toString());
            double successRate = p * ( 1 - cost) + q * (1 - diff) + (1 - p - q)*grow;

            recommendResultDao.updateColumn(tableName,"successRate",successRate, Integer.parseInt(result.get("member1").toString()));
        }
    }

    public void updateCost(String tableName,int teamSize,double p,double q){
        List<HashMap<String ,Object>> results = recommendResultDao.getRecommendResult(tableName);

        for (HashMap<String, Object> result:results) {
            int members[] = new int[teamSize];
            for (int i = 1; i <= teamSize; i ++){
                String key = "member" + i;
                members[i - 1] = Integer.parseInt(result.get(key).toString());
            }
            double ability[] = teamSuccessRate.loadTeamAbility(members,teamSize);
            double diff = abilityDiff.getAbilityDiff(ability,teamSize);
            double grow = abilityGrow.getGrowSpace(ability,teamSize);
            double cost = Double.parseDouble(result.get("cost").toString());

            double successRate = p * ( 1 - cost) + q * (1 - diff) + (1 - p - q)*grow;
            recommendResultDao.updateColumn(tableName,"successRate",successRate, Integer.parseInt(result.get("member1").toString()));
            recommendResultDao.updateColumn(tableName,"diff",diff, Integer.parseInt(result.get("member1").toString()));
            recommendResultDao.updateColumn(tableName,"grow",grow, Integer.parseInt(result.get("member1").toString()));

        }
    }

}
