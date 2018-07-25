package cn.springmvc.service;

import cn.springmvc.dao.CompetitorDao;
import cn.springmvc.model.CoffiTable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by YLT on 2018/7/24.
 */
@Component
public class KaggleWeb {
    @Autowired
    private Recomend1 recomend1;

    @Autowired
    private RecommendSetGet recommendSetGet;

    @Autowired
    private TeamSuccessRate teamSuccessRate;

    @Autowired
    private TeamCost teamCost;

    @Autowired
    private AbilityDiff abilityDiff;

    @Autowired
    private AbilityGrow abilityGrow;

    @Autowired
    private DataPreLoad dataPreLoad;

    @Autowired
    private CompetitorDao competitorDao;

    boolean flag = false;
    public String recommend(String userName, String platform, int memberNeeded, String skill){
        if (flag == false){
            dataPreLoad.dataLoad();
            dataPreLoad.coffiMapLoad();
            flag = true;
        }
        int userId = competitorDao.getCompetitorId(userName);
        return recommendationWeb(userId,platform,memberNeeded,skill).toString();
    }


    public JSONObject recommendationWeb(int userId, String platform, int memberNeeded, String skill){
        JSONObject resultJson = new JSONObject();
        int[] teamMember = new int[memberNeeded + 1];
        String[] teamMemberStr = new String[memberNeeded + 1];
        teamMember[0] = userId;
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet6();
        allCompetitors.remove(allCompetitors.indexOf(userId));

        for (int i = 1; i <= memberNeeded; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = teamSuccessRate.getTeamSuccessRateMutual2(teamMember, i + 1, 0.7);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            teamMember[i] = toChose;
            allCompetitors.remove(allCompetitors.indexOf(toChose));
        }

        double[] memberAbility = teamSuccessRate.loadTeamAbility(teamMember, memberNeeded + 1);
        double successRate = 0;

        double [] memberCost = teamCost.getTeamCost3(teamMember,memberNeeded + 1,0.7);
        double [] memberDiff = abilityDiff.getAbilityDiff6Array(memberAbility,memberNeeded + 1);
        double [] memberGrow = abilityGrow.getGrowSpace3(memberAbility,memberNeeded + 1);

        for (int i = 0; i <= memberNeeded; i ++){
            JSONObject memberJson = new JSONObject();
            CoffiTable coffi = DataPreLoad.coffiMap.get(teamMember[i]);
            double tempSuccessrate = (1-memberCost[i]) * coffi.getCostPercent() *3.2 + (1-memberDiff[i]) * coffi.getDiffPercent() + memberGrow[i] * coffi.getGrowPercent()*3.2;
            successRate += tempSuccessrate;
            memberJson.put("closeness", 1-memberCost[i]);
            memberJson.put("diff",memberDiff[i]);
            memberJson.put("grow",memberGrow[i]);
            memberJson.put("willEach",tempSuccessrate);
            memberJson.put("closenessPercent",coffi.getCostPercent());
            memberJson.put("diffPercent",coffi.getDiffPercent());
            memberJson.put("growPercent",coffi.getGrowPercent());
            String memberName = competitorDao.getCompetitorName(teamMember[i]);
            teamMemberStr[i] = memberName;
            resultJson.put(memberName,memberJson);
        }

        resultJson.put("members",teamMemberStr);
        resultJson.put("willingness",successRate/3.2/(memberNeeded+1));
        return resultJson;
    }
}
