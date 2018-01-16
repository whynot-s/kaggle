package cn.springmvc.service;

import cn.springmvc.dao.CompetitorAbilityDao;
import cn.springmvc.dao.RecommendResultDao;
import cn.springmvc.dao.RelationGenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YLT on 2018/1/12.
 */
@Service
public class Recomend1 {

    @Autowired
    private TeamCost teamCost;

    @Autowired
    private AbilityDiff abilityDiff;

    @Autowired
    private AbilityGrow abilityGrow;

    @Autowired
    private CompetitorAbilityDao competitorAbilityDao;

    @Autowired
    private RelationGenDao relationGenDao;

    @Autowired
    private TestDataGet testDataGet;

    @Autowired
    private RecommendResultDao recommendResultDao;

    /*
    * 加载团队成员的能力值
    * */
    public double[] loadTeamAbility(int[] team, int teamSize) {
        double memberAbility[] = new double[teamSize];
        for (int i = 0; i < teamSize; i++) {
            double score = competitorAbilityDao.getCompetitorAbility(team[i], "totalToOne");
            memberAbility[i] = score;
        }
        return memberAbility;
    }

    public List<Integer> getRecommendSet(int competitorId, double abilityDiff) {
        List<Integer> allCompetitors = competitorAbilityDao.getCompetitorIds();
        allCompetitors.remove(allCompetitors.indexOf(competitorId));

        for (int i = 0; i < allCompetitors.size(); ) {
            double diff = competitorAbilityDao.getCompetitorAbility(competitorId, "totalToOne") - competitorAbilityDao.getCompetitorAbility(allCompetitors.get(i), "totalToOne");

            if (Math.abs(diff) > abilityDiff && relationGenDao.recordExistOrNot(competitorId, allCompetitors.get(i)) == 0) {
                allCompetitors.remove(i);
            } else {
                i++;
            }
        }
        return allCompetitors;
    }

    public void experiment() {
        List<Integer> testCompetitors = testDataGet.getCompetitorToTest();
        for (int i = 0; i < testCompetitors.size();i++){
            System.out.println(testCompetitors.get(i));
            recommendForCompetitor(testCompetitors.get(i), 4, 0.4, 0.5, 0.3, 0.7);
        }
    }

    /*
    *推荐流程
    * 被推荐者、团队大小、团队沟通代价系数、团队能力差异系数、cost中协作关系系数
    */
    public int[] recommendForCompetitor(int competitorId, int teamSize, double Diff, double p, double q, double costPercent) {
        int[] teamMember = new int[teamSize];
        teamMember[0] = competitorId;
        List<Integer> allCompetitors = getRecommendSet(competitorId, Diff);

        for (int i = 1; i < teamSize; i++) {
            int toChose = -1;
            double successRate = -1;
            for (int competitor : allCompetitors) {
                teamMember[i] = competitor;
                double tempSuccessRate = getTeamSuccessRate(teamMember, i + 1, p, q, costPercent);
                if (tempSuccessRate > successRate) {
                    successRate = tempSuccessRate;
                    toChose = competitor;
                }
            }
            teamMember[i] = toChose;
            allCompetitors.remove(allCompetitors.indexOf(toChose));
        }

        double cost = teamCost.getTeamCost(teamMember, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(loadTeamAbility(teamMember, teamSize), teamSize);
        double grow = abilityGrow.getGrowSpace(loadTeamAbility(teamMember, teamSize), teamSize);

        if (teamSize == 2){
            recommendResultDao.insert2(teamMember[0], teamMember[1], cost, diff, grow,"recommendResult_2");
        }else if (teamSize == 3){
            recommendResultDao.insert3(teamMember[0], teamMember[1], teamMember[2], cost, diff, grow,"recommendResult_3");
        }else if(teamSize == 4){
            recommendResultDao.insert4(teamMember[0], teamMember[1], teamMember[2],teamMember[3], cost, diff, grow,"recommendResult_4");
        }
        return teamMember;
    }

    /*
    * 组队成功率计算公式
    * */
    public double getTeamSuccessRate(int[] team, int teamSize, double p, double q, double costPercent) {
        double[] memberAbility = loadTeamAbility(team, teamSize);

        double cost = teamCost.getTeamCost(team, teamSize, costPercent);
        double diff = abilityDiff.getAbilityDiff(memberAbility, teamSize);
        double grow = abilityGrow.getGrowSpace(memberAbility, teamSize);

        double resultRate = p * (1 - cost) + q * (1 - diff) + (1 - p - q) * grow;
        return resultRate;
    }

    public double[] getPRRate(int[] team, int teamSize) {
        int competitor1 = team[0];
        double[] result = new double[2];
        int Time = 0;
        for (int i = 1; i < teamSize; i++) {
            int competitor2 = team[i];
            if (relationGenDao.recordExistOrNot(competitor1, competitor2) == 1) {
                Time++;
            }
        }
        if (Time != 0) {
            result[0] = 1.0;
        }
        result[1] = Time / ((double) (teamSize - 1));
        return result;
    }
}
