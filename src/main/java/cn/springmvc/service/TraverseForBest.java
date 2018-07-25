package cn.springmvc.service;

import cn.springmvc.dao.RecommendResultDao;
import cn.springmvc.util.Combine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YLT on 2018/3/22.
 */
@Component
public class TraverseForBest {

    @Autowired
    private RecommendSetGet recommendSetGet;

    @Autowired
    private Combine combine;

    @Autowired
    private TeamSuccessRate teamSuccessRate;

    @Autowired
    private TeamCost teamCost;

    @Autowired
    private AbilityDiff abilityDiff;

    @Autowired
    private AbilityGrow abilityGrow;

    @Autowired
    private RecommendResultDao recommendResultDao;


    public int[] getBestRecommendation(int competitorId, int teamSize, double p, double q, double r, double costPercent) {
        System.out.println(competitorId);
        String[] teamMemberstr = new String[teamSize];
        int[] teamMember = new int[teamSize];
        int[] endMember = new int[teamSize];
        double successRate = -1;

        List<Integer> recommendSet2 = recommendSetGet.getRecommendSet2(competitorId, 0.3);
        List<String> CombineResult = new ArrayList<String>();
        combine.combination("", recommendSet2, teamSize - 1, CombineResult);

        for (int i = 0; i < CombineResult.size(); i++) {
            teamMemberstr = CombineResult.get(i).split(", ");
            for (int w = 0; w < teamSize - 1; w++) {
                teamMember[w] = Integer.parseInt(teamMemberstr[w]);
            }
            teamMember[teamSize - 1] = competitorId;
            double tempSuccessRate = teamSuccessRate.getTeamSuccessRate(teamMember, teamSize, p, q, r, costPercent);
            if (tempSuccessRate > successRate) {
                successRate = tempSuccessRate;
                for (int w = 0; w < teamSize; w++) {
                    endMember[w] = teamMember[w];
                }
            }
        }

        double cost = teamCost.getTeamCost(endMember, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(teamSuccessRate.loadTeamAbility(endMember, teamSize), teamSize);
        double grow = abilityGrow.getGrowSpace(teamSuccessRate.loadTeamAbility(endMember, teamSize), teamSize);
        double resultRate = p * (1 - cost) + q * (1 - diff) + r * grow;

        if (teamSize == 2) {
            recommendResultDao.insert2(endMember[0], endMember[1], cost, diff, grow, resultRate, "recommendResult_2_best");
        } else if (teamSize == 3) {
            recommendResultDao.insert3(endMember[0], endMember[1], endMember[2], cost, diff, grow, resultRate, "recommendResult_3_best");
        } else if (teamSize == 4) {
            recommendResultDao.insert4(endMember[0], endMember[1], endMember[2], endMember[3], cost, diff, grow, resultRate, "recommendResult_4_best");
        }
        return endMember;
    }

    /*
   *推荐流程
   * 被推荐者、团队大小、团队沟通代价系数、团队能力差异系数、cost中协作关系系数
   * 遍历求最优推荐结果
   */
    public int[] recommendBestSituation2(int competitorId, int teamSize, double p, double q, double r, double costPercent) {
        int[] teamMember = new int[2];
        int[] end = new int[2];
        teamMember[0] = competitorId;
//        List<Integer> recommendSet2 = recommendSetGet.getRecommendSet2(competitorId, 0.3);
        //List<Integer> recommendSet2 = recommendSetGet.getRecommendSet3(competitorId, 0.3);
        List<Integer> recommendSet2 = recommendSetGet.getRecommendSet4();
        recommendSet2.remove(recommendSet2.indexOf(competitorId));
        end[0] = teamMember[0];
        double successRate = -1;

        for (int i = 0; i < recommendSet2.size(); i++) {
            teamMember[1] = recommendSet2.get(i);
            double tempSuccessRate = teamSuccessRate.getTeamSuccessRate(teamMember, 2, p, q, r, costPercent);
            if (tempSuccessRate > successRate) {
                successRate = tempSuccessRate;
                end[1] = teamMember[1];
            }
        }

        double cost = teamCost.getTeamCost(end, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(teamSuccessRate.loadTeamAbility(end, teamSize), teamSize);
        double grow = abilityGrow.getGrowSpace(teamSuccessRate.loadTeamAbility(end, teamSize), teamSize);
        double resultRate = p * (1 - cost) + q * (1 - diff) + r * grow;

        recommendResultDao.insert2(end[0], end[1], cost, diff, grow, resultRate, "recommendResult_2_best");

        return end;
    }

    public int[] recommendBestSituation3(int competitorId, int teamSize, double p, double q, double r, double costPercent) {
        int[] teamMember = new int[3];
        int[] end = new int[3];
        teamMember[0] = competitorId;
        /*List<Integer> recommendSet2 = recommendSetGet.getRecommendSet2(competitorId, 0.3);*/
        List<Integer> recommendSet2 = recommendSetGet.getRecommendSet4();
        recommendSet2.remove(recommendSet2.indexOf(competitorId));

        end[0] = teamMember[0];
        double successRate = -1;
        //System.out.println("recommend set's size:" + recommendSet2.size());
        for (int i = 0; i < recommendSet2.size(); i++) {
            teamMember[1] = recommendSet2.get(i);
            for (int j = i + 1; j < recommendSet2.size(); ) {
                if (i != j) {
                    teamMember[2] = recommendSet2.get(j);
                    double tempSuccessRate = teamSuccessRate.getTeamSuccessRate(teamMember, 3, p, q, r, costPercent);
                    if (tempSuccessRate > successRate) {
                        successRate = tempSuccessRate;
                        end[1] = teamMember[1];
                        end[2] = teamMember[2];
                    }
                }
                j++;
            }
        }

        double cost = teamCost.getTeamCost(end, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(teamSuccessRate.loadTeamAbility(end, teamSize), teamSize);
        double grow = abilityGrow.getGrowSpace(teamSuccessRate.loadTeamAbility(end, teamSize), teamSize);
        double resultRate = p * (1 - cost) + q * (1 - diff) + r * grow;

        recommendResultDao.insert3(end[0], end[1], end[2], cost, diff, grow, resultRate, "recommendResult_3_best");

        return end;
    }

    public int[] recommendBestSituation3copy(int competitorId, int teamSize, double p, double q, double r, double costPercent) {
        int[] teamMember = new int[3];
        int[] end = new int[3];
        teamMember[0] = competitorId;
        /*List<Integer> recommendSet2 = recommendSetGet.getRecommendSet2(competitorId, 0.3);*/
        List<Integer> recommendSet2 = recommendSetGet.getRecommendSet4();
        recommendSet2.remove(recommendSet2.indexOf(competitorId));

        end[0] = teamMember[0];
        double successRate = -1;
        //System.out.println("recommend set's size:" + recommendSet2.size());
        for (int i = 0; i < recommendSet2.size(); i++) {
            teamMember[1] = recommendSet2.get(i);
            for (int j = i + 1; j < recommendSet2.size(); ) {
                if (i != j) {
                    teamMember[2] = recommendSet2.get(j);
                    double tempSuccessRate = teamSuccessRate.getTeamSuccessRate(teamMember, 3, p, q, r, costPercent);
                    if (tempSuccessRate > successRate) {
                        successRate = tempSuccessRate;
                        end[1] = teamMember[1];
                        end[2] = teamMember[2];
                    }
                }
                j++;
            }
        }
        return end;
    }


    public int[] recommendBestSituation3Mutual(int competitorId, int teamSize, double costPercent) {
        int[] teamMember = new int[3];
        int[] end = new int[3];
        teamMember[0] = competitorId;
        List<Integer> recommendSet2 = recommendSetGet.getRecommendSet6();
        recommendSet2.remove(recommendSet2.indexOf(competitorId));

        end[0] = teamMember[0];
        double successRate = -1;
        long time = System.currentTimeMillis();
        for (int i = 0; i < recommendSet2.size(); i++) {
            teamMember[1] = recommendSet2.get(i);

            for (int j = i + 1; j < recommendSet2.size(); ) {
                if (i != j) {
                    teamMember[2] = recommendSet2.get(j);
                    double tempSuccessRate = teamSuccessRate.getTeamSuccessRateMutual2(teamMember, 3, costPercent);
                    if (tempSuccessRate > successRate) {
                        successRate = tempSuccessRate;
                        end[1] = teamMember[1];
                        end[2] = teamMember[2];
                    }
                }
                j++;
            }

        }
        System.out.println(System.currentTimeMillis() - time);
        double resultRate = teamSuccessRate.getTeamSuccessRateMutual2(end,3,costPercent);
        //recommendResultDao.insert3(end[0], end[1], end[2], 0, 0, 0, resultRate, "recommendResult_3_best");
        return end;
    }

    public void exp(String tableName,int competitorId, int teamSize, double p, double q, double r, double costPercent){

        int [] end = new int[teamSize];
        end = recommendBestSituation3copy(competitorId,teamSize,p,q,r,costPercent);
        double cost = teamCost.getTeamCost(end, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(teamSuccessRate.loadTeamAbility(end, teamSize), teamSize);
        double grow = abilityGrow.getGrowSpace(teamSuccessRate.loadTeamAbility(end, teamSize), teamSize);
        double resultRate = p * (1 - cost) + q * (1 - diff) + r * grow;

        recommendResultDao.insert3(end[0], end[1], end[2], cost, diff, grow, resultRate, tableName);
    }
}
