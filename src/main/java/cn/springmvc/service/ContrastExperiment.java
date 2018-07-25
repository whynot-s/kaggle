package cn.springmvc.service;

import cn.springmvc.dao.RecommendResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YLT on 2018/4/13.
 */
@Component
public class ContrastExperiment {
    @Autowired
    private RecommendSetGet recommendSetGet;

    @Autowired
    private AbilityDiff abilityDiff;

    @Autowired
    private TeamSuccessRate teamSuccessRate;

    @Autowired
    private RecommendResultDao recommendResultDao;

    @Autowired
    private AbilityGrow abilityGrow;

    public void diffCompare(int competitorId, int teamSize, double costPercent){
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet6();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;

        for (int i = 1; i < teamSize; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = 1 - abilityDiff.getAbilityDiff(teamSuccessRate.loadTeamAbility(teamMember,i+1), i + 1);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            teamMember[i] = toChose;
            allCompetitors.remove(allCompetitors.indexOf(toChose));
        }
        double resultRate = teamSuccessRate.getTeamSuccessRateMutual2Intimacy(teamMember,teamSize,costPercent);
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], 0, 0, 0, resultRate,"recommendResult_2_test_diff");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], 0, 0, 0,resultRate, "recommendResult_3_test_diff");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], 0, 0, 0,resultRate, "recommendResult_4_test_diff");
        }
    }


    public void growCompare(int competitorId, int teamSize, double costPercent){
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet6();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;

        for (int i = 1; i < teamSize; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = abilityGrow.getGrowSpace(teamSuccessRate.loadTeamAbility(teamMember,i+1), i + 1);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            teamMember[i] = toChose;
            allCompetitors.remove(allCompetitors.indexOf(toChose));
        }
        double resultRate = teamSuccessRate.getTeamSuccessRateMutual2Intimacy(teamMember,teamSize,costPercent);
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], 0, 0, 0, resultRate,"recommendResult_2_test_grow");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], 0, 0, 0,resultRate, "recommendResult_3_test_grow");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], 0, 0, 0,resultRate, "recommendResult_4_test_grow");
        }
    }

    public void randomCompare(int competitorId, int teamSize, double costPercent){
        List<Integer> allCompetitors = recommendSetGet.getRecommendSet6();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));
        int length = allCompetitors.size();
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;
        ArrayList<Integer> selected = new ArrayList<Integer>();

        for (int index = 1; index < teamSize;index ++){
            int randNum = -1;
            do{
                randNum = (int) (Math.random() * length);
            }while (selected.contains(allCompetitors.get(randNum)));
            selected.add(allCompetitors.get(randNum));
            teamMember[index] = allCompetitors.get(randNum);
        }

        double resultRate = teamSuccessRate.getTeamSuccessRateMutual2Intimacy(teamMember,teamSize,costPercent);
        if (teamSize == 2) {
            recommendResultDao.insert2(teamMember[0], teamMember[1], 0, 0, 0, resultRate,"recommendResult_2_random");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], 0, 0, 0,resultRate, "recommendResult_3_random");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2], teamMember[3], 0, 0, 0,resultRate, "recommendResult_4_random");
        }
    }
}
